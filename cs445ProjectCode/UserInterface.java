import java.io.*;

import javax.lang.model.util.ElementScanner14;

public class UserInterface 
{
    //booleans for reply from server.
    boolean isGameOver = false; 
    boolean mustReiterateChain = false;
    boolean attemptedWordIsCorrect = false; 
    boolean isReiterationCorrect = false; 

    public UserInterface ()
    {

    }

    /* Print gamestate gui 
    * Client side
    */
    public void printInitialState()
    {
        System.out.println("***********************************************************************");
        System.out.println("***********************************************************************");
        System.out.println("*                                                                     *");
        System.out.println("*                                                                     *");
        System.out.println("*                                                                     *");
        System.out.println("*                       WELCOME TO WORD CHAIN!                        *");
        System.out.println("*                                                                     *");
        System.out.println("* RULES: Provide a word to start the chain. Player 2 will than        *");
        System.out.println("* reiterate the chain and then add a new word. Than once its your     *");
        System.out.println("* turn again, you must too reiterate the chain than add a new word.   *");
        System.out.println("*                                                                     *");
        System.out.println("* NOTE: The max size a chain can be is 200 words                      *");
        System.out.println("*                                                                     *");
        System.out.println("*                                                                     *");
        System.out.println("*                                                                     *");
        System.out.println("***********************************************************************");
        System.out.println("\n\nBEGIN: Please provide the first word in the chain.");
    }

    /*
    * Print reply from server for  
    */
    // public void printServerReply(String reply)
    // {
    //     if(this.isGameOver)
    //     {
    //         this.printGameOver();
    //         //no need to reset boolean because game is over
    //     }
    //     else if(this.isReiterationCorrect)
    //     {
    //         this.printReiterationCorrect();
    //         this.isReiterationCorrect = false; //must reset
    //     }
    //     else if(this.attemptedWordIsCorrect)
    //     {
    //         this.printAttemptedWordIsCorrect();
    //         this.attemptedWordIsCorrect = false; //must reset
    //     }
    //     else if(this.mustReiterateChain)
    //     {
    //         this.printMustReiterateChain();
    //         this.mustReiterateChain = false; //must reset
    //     }
    //     else
    //     {
    //         System.out.println("There was an error");
    //     }
    //     System.out.println("REPLY RECIEVED: "+ reply);
    // }

    public String printGameOver()
    {
        String reply = "";
        reply += "***********************************************************************\n";
        reply += "***********************************************************************\n"; 
        reply += "*                                                                     *\n";
        reply += "*                                                                     *\n";
        reply += "*                                                                     *\n";
        reply += "*                            GAME OVER!                               *\n";
        reply += "*                                                                     *\n";
        reply += "*                                                                     *\n";
        reply += "*                                                                     *\n";
        reply += "***********************************************************************\n";
        return reply; 
    }

    //for server
    public String printReiterationCorrect(String[] wordArray)
    {
        String reply = "";
        reply += ("***********************************************************************\n");
        reply += ("***********************************************************************\n");
        reply += ("*                                                                     *\n");
        reply += ("*                                                                     *\n");
        reply += ("*                                                                     *\n");
        reply += ("*                        RIETERATION CORRECT!                         *\n");
        reply += ("*                                                                     *\n");
        reply += ("*                                                                     *\n");
        reply += ("*                                                                     *\n");
        reply += ("***********************************************************************\n");
        String chain = "CHAIN: ";
        for(int i = 0; i<wordArray.length; i++)
        {
            if(wordArray[i].equals("INVALID"))
            {
                break;
            }
            else
            {
                chain += wordArray[i] + ", ";
            }
        }
        reply += chain;
        reply += ("\n\nPlease provide a NEW word in the chain.\n");
        return reply;
    }
    
    //for server
    public String printAttemptedWordIsCorrect(String word, int currentWord)
    {
        String reply = "";
        reply += ("***********************************************************************\n");
        reply += ("***********************************************************************\n");
        reply += ("*                                                                     *\n");
        reply += ("*                                                                     *\n");
        reply += ("*                                                                     *\n");
        reply += ("     ATTEMPTED WORD CORRECT: '" + word + "'                            \n");
        reply += ("*                                                                     *\n");
        reply += ("*                   CORRECT! (" + currentWord + "/x)                  *\n");
        reply += ("*                                                                     *\n");
        reply += ("***********************************************************************\n");
        reply += "Reiterate next word: ";
        return reply;
    }

    //for server
    public void printMustReiterateChain()
    {

    }


}