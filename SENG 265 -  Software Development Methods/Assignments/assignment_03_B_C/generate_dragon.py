import sys
import math
import Line_Point_colour


'''
purpose
	write to stdout a regular polygon with s sides and first vertex at (x0,y0)
preconditions
	None
'''

# process the command line arguments
if len(sys.argv) != 4:
	print >> sys.stderr, 'Syntax: ' + sys.argv[0] + ' x0 y0'
	sys.exit(1)
try:
	x0 = float(sys.argv[1])
	y0 = float(sys.argv[2])
	colour = str(sys.argv[3])

except ValueError:
	print >> sys.stderr, 'Syntax: ' + sys.argv[0] + ' x0 y0 '
	sys.exit(2)


# generate s lines, each rotated by the central angle
def square (x0, y0,colour):
	central_angle = math.pi / 2
	p0 = Line_Point_colour.Point(x0, y0)
	x = 4
	while x > 0:
		p1 = Line_Point_colour.Point(p0.x, p0.y)
		p1.rotate(central_angle)
		print 'line', Line_Point_colour.Line(p0, p1,colour)
		p0 = p1
		x = x - 1

square(x0, y0,colour)
	

