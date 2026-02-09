package main

import (
	"encoding/binary"
	"fmt"
	"image"
	"image/color"
	"image/png"
	"os"
)

func bytesToBits(data []byte) []uint8 {
	bits := make([]uint8, 0, len(data)*8)
	for _, b := range data {
		for i := 0; i < 8; i++ {
			bits = append(bits, (b>>i)&1)
		}
	}
	return bits
}

func bitsToBytes(bits []uint8) []byte {
	data := make([]byte, 0, len(bits)/8)
	for i := 0; i < len(bits); i += 8 {
		var b byte = 0
		for j := 0; j < 8 && i+j < len(bits); j++ {
			b |= bits[i+j] << j
		}
		data = append(data, b)
	}
	return data
}

func writeMessageWithHeader(message, inputPath, outputPath string) error {
	file, _ := os.Open(inputPath)
	defer file.Close()

	img, _ := png.Decode(file)
	bounds := img.Bounds()
	newImg := image.NewRGBA(bounds)

	msgBytes := []byte(message)
	header := make([]byte, 4)
	binary.BigEndian.PutUint32(header, uint32(len(msgBytes)))
	data := append(header, msgBytes...)

	bits := bytesToBits(data)
	bitIndex := 0

	for y := bounds.Min.Y; y < bounds.Max.Y; y++ {
		for x := bounds.Min.X; x < bounds.Max.X; x++ {
			original := color.RGBAModel.Convert(img.At(x, y)).(color.RGBA)
			if bitIndex < len(bits) {
				original.B = (original.B & 0xFE) | bits[bitIndex]
				bitIndex++
			}
			newImg.Set(x, y, original)
		}
	}

	outfile, _ := os.Create(outputPath)
	defer outfile.Close()
	return png.Encode(outfile, newImg)
}

func readMessageWithHeader(inputPath string) (string, error) {
	file, _ := os.Open(inputPath)
	defer file.Close()

	img, _ := png.Decode(file)
	bounds := img.Bounds()

	// 1️⃣ Extrair os 32 primeiros bits → 4 bytes de tamanho
	headerBits := make([]uint8, 32)
	for i := 0; i < 32; i++ {
		x := i % bounds.Dx()
		y := i / bounds.Dx()
		_, _, b, _ := img.At(bounds.Min.X+x, bounds.Min.Y+y).RGBA()
		headerBits[i] = uint8((b >> 8) & 1)
	}
	headerBytes := bitsToBytes(headerBits)
	msgLength := binary.BigEndian.Uint32(headerBytes)

	totalBits := int(msgLength) * 8
	bits := make([]uint8, totalBits)
	for i := 0; i < totalBits; i++ {
		pos := i + 32 // ignorar header
		x := pos % bounds.Dx()
		y := pos / bounds.Dx()
		_, _, b, _ := img.At(bounds.Min.X+x, bounds.Min.Y+y).RGBA()
		bits[i] = uint8((b >> 8) & 1)
	}

	data := bitsToBytes(bits)
	return string(data), nil
}

func main() {
	if len(os.Args) < 3 {
		fmt.Println("Usage:")
		fmt.Println("  stenographer write \"message\" input.png output.png")
		fmt.Println("  stenographer read input.png")
		return
	}

	mode := os.Args[1]

	switch mode {
	case "write":
		if len(os.Args) < 5 {
			fmt.Println("Usage: stenographer write \"message\" input.png output.png")
			return
		}

		err := writeMessageWithHeader(os.Args[2], os.Args[3], os.Args[4])
		if err != nil {
			fmt.Println("Error: ", err)
			return
		}

		fmt.Println("Message was written to file successfully")

	case "read":
		msg, err := readMessageWithHeader(os.Args[2])
		if err != nil {
			fmt.Println("Error: ", err)
			return
		}
		fmt.Println("Message extracted: ", msg)

	default:
		fmt.Println("Unknown mode: ", mode)

	}

}
