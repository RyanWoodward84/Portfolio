#! /bin/bash

#Changing all of the command line arguments into variables

xCoor=$1
yCoor=$2
colour=$3
scale=$4
count=$5
scaleFactor="."$scale

#Checking variables to ensure proper bounds

if [ $xCoor -lt 0 ]; then
    echo "X coordinate must > 0"
    exit
fi

if [ $xCoor -ge 100 ]; then
    echo "X coordinate must be less than or equal to 100"
    exit
fi

if [ $yCoor -lt 0 ]; then
    echo "Y coordinate must be > 0"
    exit
fi

if [ $yCoor -ge 200 ]; then
    echo "Y coordinate must be less than or equal to 200"
    exit
fi

if [ $scale -lt 0 ]; then
    echo "Scale factor must be an integer input greater than zero"
    exit
fi

python generate_shape.py $xCoor $yCoor $colour $scaleFactor $count > dragon0.txt
python rotate_scale_translate_dragon.py -a 1.59 dragon0.txt > dragon1.txt
python transformer.py 4 $colour < dragon1.txt > dragon2.txt 
python lines_to_svg_colour.py dragon2.txt > dragon2.svg
