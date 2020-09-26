CFLAGS_DBG=-g -ggdb3 -Wall
CFLAGS_OPT=-O3 -Wall
CFLAGS_ARMv7=-g -fno-tree-vectorize
CFLAGS_ARMv7_NEON=-O3 -g -funroll-loops -ffast-math -fassociative-math -freciprocal-math -fno-trapping-math -ftree-vectorize -march=armv7-a -mfpu=neon-vfpv4
CFLAGS_ARMv7_NEON_DBG=-g -O0 -ggdb3 -ftree-vectorize -march=armv7-a -mfpu=neon-vfpv4

SRC=scale.c simple.c

scale: $(SRC)
	$(CC) $(CFLAGS_ARMv7_NEON) $(SRC) -o ./bin/$@

clean:
	rm -f ./bin/scale
