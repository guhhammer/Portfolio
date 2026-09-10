# Information security (Python)

Coursework from the Information Security course at PUCPR (2018): classic ciphers, key distribution, hashing and password cracking, RSA/AES timing, steganography and access control. Code translated from Portuguese; reports are the original Portuguese documents, described below.

For a non-technical reader: these are the building blocks of "how do two people exchange secrets over an open channel" and "how are passwords stored and attacked", implemented by hand to understand them.

| Folder | What it is | Files |
| --- | --- | --- |
| `caesar-cipher-kdc/` | The Caesar cipher (shift the alphabet), a brute-force attack that tries all 26 shifts, and a first sketch of a key distribution centre (KDC) that derives a session key from Bob's and Alice's keys | `caesar_cipher.py`, `crack_caesar.py`, `kdc.py`, `kdc_to_bob.py`, `alice_receive.py` |
| `session-key-exchange-kdc/` | The same idea extended: a 225-symbol shuffled alphabet, random personal keys, a KDC that issues a random session key, encryption from Bob to Alice through the KDC, and a challenge/response verification step | `persona_*.py`, `kdc.py`, `functions.py`, `bob_to_kdc_to_bob.py`, `alice_receives.py`, `alice_verification.py`, `bob_checks_and_replies.py`, `alice_end.py` |
| `md5-password-cracking/` | Assignment in three items: store users with MD5-hashed 4-character passwords, brute-force the hashes and time it, then harden the scheme. Includes the brute-force script (ported to Python 3), the timing results for 40 test users, the spreadsheet and the report | `md5_bruteforce.py`, `test-users.csv`, `cracking-times.txt/.xlsx`, `report.docx`, `assignment.md`, `screenshots/` |
| `rsa-key-generation-timing/` | Measured time to generate RSA key pairs of 512 to 8192 bits and AES keys (three runs each), with screenshots and the report (RSA 1024-bit public key: about 0.8 s average) | PNG screenshots, `report.pdf` |
| `steganography-report/` | Hiding an image and a text inside another image and extracting them back; my own run (cover image, hidden image, results) and an example run, with the written report | `my-run/`, `example-run/`, `report.pdf` |
| `access-control-matrix/` | A subject/object access matrix check (users versus files and read/write/execute rights) | `access_matrix.py` |
| `twitter-trends-analysis/` | Pair project: a Tweepy stream listener counting Portuguese tweets about series, football, soap operas and movies, with the analysis report and charts (API credentials are read from environment variables) | `twitter_stream_counter.py`, `report.docx`, `chart-*.png`, `notes.md` |
| `reports/` | Written assignments: security best practices, norms and standards; exploratory research on biometrics (exam); my notes of the first class | DOCX/PDF |

## Run

```bash
cd caesar-cipher-kdc && python3 caesar_cipher.py      # encrypt, decrypt and print the shifted alphabet
python3 crack_caesar.py                                # brute-force the 26 shifts
cd ../session-key-exchange-kdc && python3 alice_end.py # full Bob -> KDC -> Alice exchange with verification
```

Every script compiles with Python 3.13 (`python -m py_compile`).
