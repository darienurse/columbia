'''
Created on Feb 9, 2012

@author: Darien
'''

state = []
name = []
address = []
city = []
zip = []
longitude = []
latitude = []
map1 = {}
map2 = {}


def partA(file):
    file = open ('markets.tsv','r')
    lines = file.readlines()
    num = 0
    for x in lines:
        thisline = x.split("\t")
        state.append(thisline[0])
        name.append(thisline[1])
        address.append(thisline[2])
        city.append(thisline[3])
        zip.append(thisline[4])
        longitude.append(thisline[5])
        latitude.append(thisline[6].replace("\n",""))
    for x in range(len(name)):
        map1[name[x]] = zip[x]
    for x in range(len(name)):
        map2[zip[x]] = city[x]
    return map1, map2
            
         
def partB(s, n, a, c, z):
    add = "{1} \n{2} \n{3}, {1}  {4}".format(s, n, a, c, z)
    return add

def partC():
    partA(file)
    input = ""
    while (input != "quit"):
        input=(raw_input('Please enter a city or zip code:'))
        for x in range(len(city)/2):
            if input == city[x]:
                print (partB(state[x], name[x], address[x], city[x], zip[x]+"\n"))
        for x in range(len(zip)/2):
            if input == zip[x]:
                print (partB(state[x], name[x], address[x], city[x], zip[x]+"\n"))
    


results1, results2 = partA(file)
fullAddress = partB(state[0], name[0], address[0], city[0], zip[0])
partC()
#print results1
#print results2
#print fullAddress

