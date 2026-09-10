# Steganography CLI (Go)

A command-line tool that hides a text message inside a PNG image and extracts it again. The message length is written as a 4-byte header, then every bit of the message replaces the least-significant bit of the blue channel of consecutive pixels, so the picture looks unchanged to the eye.

```bash
go build -o steganography .
./steganography write "meet at noon" dog.png secret.png
./steganography read secret.png
# Message extracted: meet at noon
```

`dog.png` and `hotdog.png` are sample carrier images. The tool refuses messages larger than the pixel count and reports clear errors for missing or non-PNG files.
