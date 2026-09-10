# Memory allocation exercises

## Placement algorithms

For two requests, 16K and 21K, against the free-list of the exercise, which hole each algorithm picks:

| Algorithm | 16K request | 21K request |
| --------- | ----------- | ----------- |
| First fit | 25K hole    | 24K hole    |
| Best fit  | 22K hole    | 24K hole    |
| Worst fit | 25K hole    | 24K hole    |
| Next fit  | 25K hole    | 24K hole    |

## Program relocation

A program assembled relative to address 0 and loaded at base 1024. Relative operands (`8+1024`) are resolved to absolute addresses by the loader.

Before relocation (relative operands):

```
1044 MOV 8+1024, REG
1040 ADD REG, 12+1024
1036 128
1032 256
1028 JMP 16+1024
1024 MOV RG, 8+1024
```

After relocation (absolute operands):

```
1044 MOV 1032, REG
1040 ADD REG, 1036
1036 128
1032 256
1028 JMP 1040
1024 MOV RG, 1032
```
