#! /bin/bash

python generate_shape.py 0 100 GoldenRod 0.9 10 > dragon0.txt
python rotate_scale_translate_dragon.py -a 1.59 dragon0.txt > dragon1.txt
python transformer.py 4 GoldenRod < dragon1.txt > dragon2.txt 
python lines_to_svg_colour.py dragon2.txt > dragon2.svg

