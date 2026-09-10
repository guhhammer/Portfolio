# Assignment 1 report: exploring a Linux system

Environment: Lubuntu inside Oracle VM VirtualBox.

## Question 1: system configuration

- **Processor:** Intel(R) Core(TM) i7-7500U CPU @ 2.70GHz; 1 physical processor, 1 core, 1 thread (as seen by the VM).
- **Memory:** total 4039388 KiB, free 2956064 KiB.
- **File system:** `/dev/sda1`, ext4, mounted read-write at `/`, size 9.8 GiB, used 5.0 GiB, available 4.8 GiB.
- **Input devices:** Power Button, Sleep Button, AT Translated Set 2 keyboard, ImExPS/2 Generic Explorer Mouse, VirtualBox USB Tablet, Video Bus, VirtualBox mouse integration.
- **Network interfaces:** `enp0s3` (Ethernet), `lo` (loopback).

## Question 2

`ll` lists with details (total 116 ...); `dir` and `ls` list the home folders (Desktop, Documents, Downloads, Pictures, Templates, Music, Public, Videos). The default path when LXTerminal opens is `/home/gustavo`.

## Question 3

- CPU usage of the program in case 1: between 76.1% and 92.3%.
- CPU usage of each of the two programs in case 2: with `sleep(0.01)` between 0.1% and 0.2%; with `sleep(0.02)` between 0.1% and 0.2%.
