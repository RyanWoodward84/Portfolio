import sys
import math as m

def train_multi_nb(labels, data):
    train_labels = open(labels, "r")
    train_data = open(data, "r")

    label_lines = train_labels.readlines()
    data_lines = train_data.readlines()

    # total number of lines
    line_count = len(label_lines)

    # number of documents in each category
    category_count = [0, 0]

    # number of occurences of a word in an array
    # count identified by category
    text_count = {}

    for line in range(len(label_lines)):
        # c is category val 0 or 1
        c = int(label_lines[line].split()[0])
        category_count[c] += 1

        for word in data_lines[line].split():
            if word not in text_count:
                text_count[word] = [0,0]
            text_count[word][c] += 1

    # number of words in the vocabulary
    vocab_count = len(text_count)

    # probability of line being in a given category
    prior = [category_count[0] / line_count, category_count[1] / line_count]

    # number of words in each category
    word_count = [0, 0]
    for key in text_count:
        word_count[0] += text_count[key][0]
        word_count[1] += text_count[key][1]

    # probability P(word | category)
    condprob = {}

    for key in text_count:
        condprob[key] = [
                (text_count[key][0] + 1) / (word_count[0] + vocab_count),
                (text_count[key][1] + 1) / (word_count[1] + vocab_count)
                ]

    train_labels.close()
    train_data.close()

    return(prior, condprob)

def apply_multi_nb(prior, condprob, data):
    f = open(data, "r")

    data_lines = f.readlines()

    guess = []

    for line in data_lines:
        score = [m.log(prior[0],2), m.log(prior[1],2)]
        for word in line.split():
            tmp = condprob.get(word)
            if tmp != None:
                score[0] += m.log(tmp[0], 2)
                score[1] += m.log(tmp[1], 2)

        if score[0] > score[1]:
            guess.append(0)
        else:
            guess.append(1)

    f.close()

    return guess

def compare(guess, test_labels):
    f = open(test_labels, "r")

    lines = f.readlines()

    actual = []

    for line in lines:
        actual.append(int(line.split()[0]))

    correct = 0

    for i in range(len(guess)):
        if guess[i] == actual[i]:
            correct += 1

    f.close()

    return(correct, len(guess))

def main():
    [prior, condprob] = train_multi_nb(sys.argv[1], sys.argv[2])

    guess = apply_multi_nb(prior, condprob, sys.argv[4])

    [correct, number] = compare(guess, sys.argv[3])

    f = open("results.txt", "w")

    f.write("Out of a total of " + str(number) + " test documents " +
    str(correct) + " were classified correctly\n")
    f.write("This gives an accuracy of " + str(correct/number))

    f.write("\n\nFiles used for training were:\n")
    f.write(sys.argv[1] + " and " + sys.argv[2])

    f.write("\nFiles used for training were:\n")
    f.write(sys.argv[3] + " and " + sys.argv[4])

    f.close()

main()