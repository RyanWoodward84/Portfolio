@ECHO OFF
"C:\Program Files (x86)\Atmel\AVR Tools\AvrAssembler2\avrasm2.exe" -S "H:\CSC230\Labs\Lab02\labels.tmp" -fI -W+ie -C V3 -o "H:\CSC230\Labs\Lab02\lab2.hex" -d "H:\CSC230\Labs\Lab02\lab2.obj" -e "H:\CSC230\Labs\Lab02\lab2.eep" -m "H:\CSC230\Labs\Lab02\lab2.map" "H:\CSC230\Labs\Lab02\lab2.asm"
