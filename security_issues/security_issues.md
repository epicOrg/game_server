Known security issues
=====================

- [ ] An attacker can create an arbitrary number of accounts with the same IP
      address. See the [account_creator.sh](account_creator.sh) script. _(Note:
      after the implementation of the encryption, the script is obsolete, but
      theoretically is still possible to exploit this bug.)_
- [x] ~~Connection is not encrypted: an attacker can sniffs passwords.~~
- [x] ~~An attacker can send a fake position to the server.~~
