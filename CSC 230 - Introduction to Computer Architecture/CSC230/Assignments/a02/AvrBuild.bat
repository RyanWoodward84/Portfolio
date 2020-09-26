@ECHO OFF
"C:\Program Files (x86)\Atmel\AVR Tools\AvrAssembler2\avrasm2.exe" -S "H:\CSC230\Assignments\a02\labels.tmp" -fI -W+ie -C V3 -o "H:\CSC230\Assignments\a02\lab6.hex" -d "H:\CSC230\Assignments\a02\lab6.obj" -e "H:\CSC230\Assignments\a02\lab6.eep" -m "H:\CSC230\Assignments\a02\lab6.map" "H:\CSC230\Labs\Lab06\numDisplay.asm"
