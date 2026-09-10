// stenographer-cli hides a text message inside a PNG image (steganography)
// by storing one message bit in the least-significant bit of each pixel's
// blue channel. A 4-byte big-endian header records the message length so it
// can be extracted again.
package main

import (
	"encoding/binary"
	"fmt"
	"image"
	"image/color"
	"image/png"
	"os"
)

const headerBits = 32 // 4-byte length prefix

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
		var b byte
		for j := 0; j < 8 && i+j < len(bits); j++ {
			b |= bits[i+j] << j
		}
		data = append(data, b)
	}
	return data
}

func decodePNG(path string) (image.Image, error) {
	file, err := os.Open(path)
	if err != nil {
		return nil, fmt.Errorf("open %s: %w", path, err)
	}
	defer file.Close()

	img, err := png.Decode(file)
	if err != nil {
		return nil, fmt.Errorf("decode %s: %w", path, err)
	}
	return img, nil
}

func writeMessage(message, inputPath, outputPath string) error {
	img, err := decodePNG(inputPath)
	if err != nil {
		return err
	}
	bounds := img.Bounds()

	msgBytes := []byte(message)
	header := make([]byte, 4)
	binary.BigEndian.PutUint32(header, uint32(len(msgBytes)))
	bits := bytesToBits(append(header, msgBytes...))

	if len(bits) > bounds.Dx()*bounds.Dy() {
		return fmt.Errorf("message needs %d pixels but the image only has %d",
			len(bits), bounds.Dx()*bounds.Dy())
	}

	newImg := image.NewRGBA(bounds)
	bitIndex := 0

	for y := bounds.Min.Y; y < bounds.Max.Y; y++ {
		for x := bounds.Min.X; x < bounds.Max.X; x++ {
			pixel := color.RGBAModel.Convert(img.At(x, y)).(color.RGBA)
			if bitIndex < len(bits) {
				pixel.B = (pixel.B & 0xFE) | bits[bitIndex]
				bitIndex++
			}
			newImg.Set(x, y, pixel)
		}
	}

	outFile, err := os.Create(outputPath)
	if err != nil {
		return fmt.Errorf("create %s: %w", outputPath, err)
	}
	defer outFile.Close()

	return png.Encode(outFile, newImg)
}

// blueLSB returns the least-significant bit of the blue channel of the pixel
// at linear position pos (row-major from the top-left corner).
func blueLSB(img image.Image, pos int) uint8 {
	bounds := img.Bounds()
	x := pos % bounds.Dx()
	y := pos / bounds.Dx()
	_, _, b, _ := img.At(bounds.Min.X+x, bounds.Min.Y+y).RGBA()
	return uint8((b >> 8) & 1)
}

func readMessage(inputPath string) (string, error) {
	img, err := decodePNG(inputPath)
	if err != nil {
		return "", err
	}
	bounds := img.Bounds()

	// The first 32 bits are the 4-byte message length.
	header := make([]uint8, headerBits)
	for i := range header {
		header[i] = blueLSB(img, i)
	}
	msgLength := binary.BigEndian.Uint32(bitsToBytes(header))

	totalBits := int(msgLength) * 8
	if headerBits+totalBits > bounds.Dx()*bounds.Dy() {
		return "", fmt.Errorf("header claims %d bytes, more than the image can hold", msgLength)
	}

	bits := make([]uint8, totalBits)
	for i := range bits {
		bits[i] = blueLSB(img, i+headerBits)
	}

	return string(bitsToBytes(bits)), nil
}

func usage() {
	fmt.Println("Usage:")
	fmt.Println("  steganography write \"message\" input.png output.png")
	fmt.Println("  steganography read input.png")
}

func main() {
	if len(os.Args) < 3 {
		usage()
		return
	}

	switch mode := os.Args[1]; mode {
	case "write":
		if len(os.Args) < 5 {
			usage()
			return
		}
		if err := writeMessage(os.Args[2], os.Args[3], os.Args[4]); err != nil {
			fmt.Println("Error:", err)
			os.Exit(1)
		}
		fmt.Println("Message was written to file successfully")

	case "read":
		msg, err := readMessage(os.Args[2])
		if err != nil {
			fmt.Println("Error:", err)
			os.Exit(1)
		}
		fmt.Println("Message extracted:", msg)

	default:
		fmt.Println("Unknown mode:", mode)
		usage()
		os.Exit(1)
	}
}
