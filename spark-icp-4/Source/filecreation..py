from random import randint
import time

# using lorem text it will generate  20 log files at specified location


def main():
    nooflogs = 1
    with open('lorem.txt', 'r') as file:  # reading content from 'lorem.txt' file
        lines = file.readlines()
        while nooflogs <= 20:
            totalline = len(lines)
            linenumber = randint(0, totalline - 10)
            with open('log/log{}.txt'.format(nooflogs), 'w') as writefile:
                writefile.write(' '.join(line for line in lines[linenumber:totalline]))
            print('creating file log{}.txt'.format(nooflogs))
            nooflogs += 1
            time.sleep(5)


if __name__ == '__main__':
    main()