#Question 4 Assignment 1
#Ryan Woodward
#V00857268

import sys
import math as m

def multinomialNaiveBayesTrain(labels, data):
    trainingLabels = open(labels, "r")
    trainingData = open(data, "r")

    labelLines = trainingLabels.readlines()
    dataLines = trainingData.readlines()

    # total number of lines
    numLines = len(labelLines)

    # number of documents in each category
    numDocuments = [0, 0]

    # number of occurences of a word in an array
    # count identified by category
    numWord = {}

    for line in range(len(labelLines)):
        # c is category val 0 or 1
        c = int(labelLines[line].split()[0])
        numDocuments[c] += 1

        for word in dataLines[line].split():
            if word not in numWord:
                numWord[word] = [0,0]
            numWord[word][c] += 1

    # number of words in the vocabulary
    numWordsinVocab = len(numWord)

    # probability of line being in a given category
    prior = [numDocuments[0] / numLines, numDocuments[1] / numLines]

    # number of words in each category
    numWordsInCat = [0, 0]
    for key in numWord:
        numWordsInCat[0] += numWord[key][0]
        numWordsInCat[1] += numWord[key][1]

    # probability P(word | category)
    condprob = {}

    for key in numWord:
        condprob[key] = [
                (numWord[key][0] + 1) / (numWordsInCat[0] + numWordsinVocab),
                (numWord[key][1] + 1) / (numWordsInCat[1] + numWordsinVocab)
                ]

    trainingLabels.close()
    trainingData.close()

    return(prior, condprob)

def multinomialNaiveBayesApply(prior, condprob, data):
    f = open(data, "r")

    dataLines = f.readlines()

    guess = []

    for line in dataLines:
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

def compare(guess, testLabels):
    f = open(testLabels, "r")

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
    [prior, condprob] = multinomialNaiveBayesTrain(sys.argv[1], sys.argv[2])

    guess = multinomialNaiveBayesApply(prior, condprob, sys.argv[4])

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