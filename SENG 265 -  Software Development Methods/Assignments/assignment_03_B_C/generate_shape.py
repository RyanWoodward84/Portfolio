import sys
import math
import Line_Point_colour


'''
purpose
	write to stdout a square with first vertex at (x0,y0)
preconditions

	None
'''
def next_shape(list, theta):
	#for each line in list, scale and rotate by theta
	for line in list:
		line.scale(0.97)
		line.rotate(theta)
	#return updated list 
	return list

def recursive_draw(list, theta, count):
	#end recursion if base case reached
	if (count == 0):
		return
	#print each line in the list
	for line in list:
		print 'line', line

	#continue with recursion
	recursive_draw(next_shape(list, theta), theta, count-1)


#************ process the command line arguments
if len(sys.argv) != 6:
	print >> sys.stderr, 'Syntax: ' + sys.argv[0] + ' x0 y0 colour theta count'
	sys.exit(1)
try:
	x0 = float(sys.argv[1])
	y0 = float(sys.argv[2])
	colour = str(sys.argv[3])	#colour of the image
	theta = math.radians(float(sys.argv[4]))	#rotating angle
	count = float(sys.argv[5])	#number of squares per spiral

except ValueError:
	print >> sys.stderr, 'Syntax: ' + sys.argv[0] + ' x0 y0 colour theta count'
	sys.exit(2)

#*********** generate s lines, each rotated by the central angle
central_angle = math.pi / 2	#90 degrees
p0 = Line_Point_colour.Point(x0, y0)

list = []
for i in range(4):
	p1 = Line_Point_colour.Point(p0.x, p0.y)
	p1.rotate(central_angle)
	line = Line_Point_colour.Line(p0, p1, colour)
	list.append(line)
	p0 = p1
	#x = x - 1

recursive_draw(list, theta, count)
