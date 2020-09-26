import sys
import Line_Point_colour
import math

'''
purpose
	parse argv:
		[-a angle] [-f factor](dont need) [-n count] [-x delta_x] [-y delta_y] [-c colour] [file(s)]
	if legal
		return dictionary of option/option value pairs
		use None for option value if option not present
		use 'file_names':L
			where L is a list of the file names and [ ] if no files present
	else
		return error message
'''
def parse_argv(argv):
	D = {'-a':None, '-n':None,   'file_names':[] }

	i = 1
	while i < len(sys.argv) and sys.argv[i][0] == '-':
		# *** duplicate option, illegal option
		if sys.argv[i] in D:
			if D[sys.argv[i]] != None:
				return 'Duplicate option: ' + sys.argv[i]
		else:
			return 'Illegal option: ' + sys.argv[i]

		# *** extract option value
		option = sys.argv[i]
		i = i + 1
		if i >= len(sys.argv):
			return 'Missing option value for: ' + sys.argv[i]
		option_value = sys.argv[i]

		# *** -a option (NEED)
		if option == '-a':
			try:
				D['-a'] = math.radians(float(option_value))
			except ValueError:
				return 'Illegal option value: ' + option + ' ' + option_value

		# *** -n option: int  (COUNT) 
		else:
			try:
				D['-n'] = int(option_value)
			except ValueError:
				return 'Illegal option value: ' + option + ' ' + option_value

		# advance to next option
		i = i + 1
	
	# add file_names to D
	D['file_names'] = sys.argv[i:]
	if D['file_names'] == [ ]:
		D['file_names'] = None
	
	return D

# ***** apply rotate, scale, colour
# recursvie method here, base case: once count hits 0 
def scale(x0, y0, x1, y1, theta):
	distance = math.sqrt((x1-x0)**2 + (y1-y0)**2)
	a = distance/(math.cos(theta)+math.sin(theta)) 
	scale = a/distance
	return scale		

def process_lines_file(file_object, options):

	for line in file_object:
		# convert L to a Line object
		L = line.split()
		point0 = Line_Point_colour.Point(float(L[1]), float(L[2]))
		point1 = Line_Point_colour.Point(float(L[3]), float(L[4]))
		#colour = Line_Point_colour.Line(str(L[5]))
		scale_factor = scale(point0.x, point0.y, point1.x, point1.y,options['-a'])
		line = Line_Point_colour.Line(point0, point1,str(L[5]))

		# rotate, scale, translate and write line count times
		for i in range(options['-n']):
			line.rotate(options['-a'])
			line.scale(scale_factor)
			print 'line', line 


# *** handle command-line arguments
options = parse_argv(sys.argv)
if type(options) == str:
	print >> sys.stderr, options
	sys.exit()

# *** apply defaults where needed
default_options = { '-a':0.0, '-n':1,  }
for option in default_options:
	if options[option] == None:
		options[option] = default_options[option]

# *** process each input file

if options['file_names'] == None:
	process_lines_file(sys.stdin, options)
else:
	for file_name in options['file_names']:
		try:
			file_object = open(file_name, 'r')
		except IOError:
			print >> sys.stderr, 'Cannot open file:', file_name
			sys.exit()
		process_lines_file(file_object, options)
