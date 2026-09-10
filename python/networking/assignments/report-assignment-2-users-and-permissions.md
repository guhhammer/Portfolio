# Assignment 2 report: users, groups and permissions

Note: my user is called `gustavo`, not `conectividade` as in the assignment text.

## Question 1

Groups of the user: `1000(gustavo), 4(adm), 24(cdrom), 27(sudo), 30(dip), 46(plugdev), 116(lpadmin), 122(sambashare)`. The user is a system administrator: it belongs to `adm`, `sudo` and `lpadmin`.

## Question 2

`chmod` granted execute permission on the file to its owner. Step 2: `-rw-rw-r--`; step 4: `-rwxrw-r--`. The `x` in the fourth position is the owner's execute bit.

## Question 3

As the guest user: read the file, allowed; execute, not allowed; delete, not allowed; rename, not allowed. The guest falls under "others" in the file permissions, so it only has `r--`, and it is not in any group with `rw-` access.

## Question 4

The guest could execute the file in the public folder and copy it there, but could not copy it into the private folder. `id guest` shows `uid=1001(guest) gid=1001(guest) groups=1001(guest),1000(gustavo)`, and `ls -l public` shows:

```
-rwxrw-r-- 1 guest   guest   30 Oct 19 14:51 test2.sh
-rwxrwxr-- 1 gustavo gustavo 30 Oct 19 14:48 test.sh
```

which confirms the group membership reported by `id`.
