
def stepTablesToReserve():
    print("\nWe have these tables to reserve:\n")

    # ...
    # apresentar lista mesas
    # ...     
                
    print("\nWhich table would you like to reserve?\n")

    tableNumber = input()

    available = True # checar há disponível. Muda esta variável depois.
    
    if(available): #verify if table is available
        print("\nYour table has been reserved.\n")
        print("\nIt was a pleasure talking with you. I hope to talk with you again soon!")
        print("Bye.\n")
        return False
    else:
        print("\nThe selected table is not available!\n")
        print("Would to select another table[yes/no]?")

        selectTableAgain = input()

        if(selectTableAgain.lower() == "yes"):

            stepTablesToReserve()

        else:

            print("\nIt was a pleasure talking with you. I hope to talk with you again soon!")
            print("Bye.\n")
            return False
                    


reserveAvailability = True # checar se há 1 mesa disponível. Mudar depois.
def stepMakeAReserve():

    global reserveAvailability

    wouldLikeToMakeAReserve = "yes"  # does not change. User's choice.
    
    if(wouldLikeToMakeAReserve == "yes"):
        if(reserveAvailability): # if there is an available table
           
            stepTablesToReserve() 
            
        else:
            print("\nUnfortunately we have no tables available. Please, try it again later!\n")

            print("Continue[yes/no]:  ", end="")

            continueChoice = input()

            if(continueChoice == "yes"):

                stepMakeAReserve()
                
            else:
                print("\nIt was a pleasure talking with you. I hope to talk with you again soon!")
                print("Bye.\n")
                return False

    else:
        print("\nIt was a pleasure talking with you. I hope to talk with you again soon!")
        print("Bye.\n")
        return False


myCellphoneG = ""
def stepEnterCellPhoneNumber():

    global myCellphoneG
    
    print("\nPlease, inform me of your cellphone number:  ", end="")

    myCellphone = str(input())
    print("\n")

    if(len(myCellphone) > 6):

        myCellphoneG = myCellphone
        return True
        
    else:
        print("\nCellphone number given is not a valid number. Enter your Number:\n")
        stepEnterCellPhoneNumber()   


myAddressG = ""
def stepEnterAddress():

    global myAddressG
    print("\nPlease, inform me of your address:  ",end="")

    myAddress = str(input())
    print("\n")

    if(len(myAddress) > 1):
        
        myAddressG = myAddress
        stepEnterCellPhoneNumber()

    else:

        print("\nAdress given is not a valid adress. Enter your Address:\n")
        stepEnterAddress()


myNameG = ""
def stepEnterName():

    global myNameG
    print("\nPlease, inform me of your name:  ", end="")

    myName = str(input())
    print("\n")

    if(len(myName) > 1):

        myNameG = myName
        stepEnterAddress()
        
    else:

        print("\nName given is not a valid name. Enter your name:\n")
        stepEnterName()



def stepEnterInformation():

    global myAddressG
    global myNameG
    global myCellphoneG
    
    stepEnterName()

    print("\nOk. Your request will be sent to your adress at ",myAddressG,
          ".\nThank you ", myNameG,
          ", for contacting our services.\nWe will contact your number ",
          myCellphoneG," if something goes wrong.\n")
    print("\nIt was a pleasure talking with you. I hope to talk with you again soon!")
    print("Bye.\n")
    return False
    
    
showMenuFirstTime = True
def stepMakeARequest():

    global showMenuFirstTime

    if(showMenuFirstTime):
        print("\nThis is our Menu:\n")
        print("...")
        showMenuFirstTime = False
    else:
        print("\nWould like to see the menu again[yes/no]?\n")

        seeAgain = input()
        
        if(seeAgain == "yes"):
            showMenuFirstTime = True
            stepMakeARequest()
        
    print("\nWhich dish would like to have?\n")

    myDish = str(input())
    
    isOnTheMenu = True # checar se tem no menu. Mudar depois.
    if(isOnTheMenu):

        print("\nAre you sure of your request[yes/no]?\n")

        sureAbout = str(input())

        if(sureAbout == "yes"):
                 
            stepEnterInformation()

        else:

            print("\nWould like to see the menu again[yes/no]?\n")

            seeMenuAgain = str(input())

            showMenuFirstTime = True

            if(seeMenuAgain == "yes"):

                stepMakeARequest()

            else:

                print("\nWould like to cancel this request[yes/no]?\n")

                cancel = str(input())

                if(cancel == "yes"):

                    print("\nIt was a pleasure talking with you. I hope to talk with you again soon!")
                    print("Bye.\n")
                    return False

                else:

                    stepMakeARequest()

    else:
        print("\nDish selected is not in the menu.\n")
        print("\nWould like to select another dish[yes/no]?\n")

        dishSelect = str(input())

        if(dishSelect == "yes"):

            stepMakeARequest()

        else:

            print("\nIt was a pleasure talking with you. I hope to talk with you again soon!")
            print("Bye.\n")
            return False
            

# MAIN:


on = True
while(on):

    print("\nHello, I will your manager for today. May I help you?\n")
    print("Would you like to reserve a table or make a request for delivery?\n")

    wouldLikeTo = str(input())

    if(wouldLikeTo.lower() == "reserve"):
        on = stepMakeAReserve()
    else:
        on = stepMakeARequest()
  
