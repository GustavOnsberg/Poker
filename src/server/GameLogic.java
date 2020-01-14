package server;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GameLogic {
    ArrayList<Integer> deck = new ArrayList<Integer>();

    public GameLogic(){
        createDeck();
    }



    public void createDeck(){
        deck.clear();
        for(int i = 0; i < 52; i++){
            deck.add(i);
        }
        Collections.shuffle(deck);
    }



    public static int evaluateHand(ArrayList<Integer> hand) throws Exception {
        if(hand.size() != 7)
            throw new Exception("Hand must hold 7 cards but had "+hand.size());
        Collections.sort(hand);

        ArrayList<Integer> handHand = new ArrayList<Integer>();
        ArrayList<Integer> handSuit = new ArrayList<Integer>();
        ArrayList<Integer> handValue = new ArrayList<Integer>();

        for (int i = 0; i < 7; i++){
            handSuit.add(hand.get(i)/13);
            handValue.add(hand.get(i)%13);
        }
        Collections.sort(handValue);


        //Check for Royal flush
        for(int i = 0; i < 4; i++){
            if(hand.contains(i*13) && hand.contains(i*13+9) && hand.contains(i*13+10) && hand.contains(i*13+11) && hand.contains(i*13+12))
                return 9000000;
        }

        //Check for Straight flush
        for(int i = 0; i < 3; i++){
            if (hand.get(i)%13 == 0){
                for(int j = 0; j < 4; j++){
                    int esValue = hand.get(0);
                    if(hand.contains(esValue+1) && hand.contains(esValue+10) && hand.contains(esValue+11) && hand.contains(esValue+12)){
                        return 8000016;
                    }
                    else if(hand.contains(esValue+1) && hand.contains(esValue+2) && hand.contains(esValue+11) && hand.contains(esValue+12)){
                        return 8000015;
                    }
                    else if(hand.contains(esValue+1) && hand.contains(esValue+2) && hand.contains(esValue+3) && hand.contains(esValue+12)){
                        return 8000014;
                    }
                    else if(hand.contains(esValue+1) && hand.contains(esValue+2) && hand.contains(esValue+3) && hand.contains(esValue+4)){
                        return 8000013;
                    }
                }
            }
            if(hand.get(i)+4==hand.get(4+i) && hand.get(0+i)/13==hand.get(4+i)/13){
                return 8000000+hand.get(4+i)%13;
            }
        }

        //Check for Four of a kind
        int[] numOfValues = new int[13];
        for(int i = 0; i < 7;i++){
            numOfValues[handValue.get(i)]++;
        }
        int highCardBonus = -1;
        int fourOfAKindBonus = -1;
        for(int i = 13; i > 0; i--){
            int checkIndex = i;
            if(checkIndex == 13)
                checkIndex = 0;
            if(numOfValues[checkIndex] == 4)
                fourOfAKindBonus = i;
            else if(numOfValues[checkIndex] != 0 && highCardBonus == -1)
                highCardBonus = i;
            if(highCardBonus != -1 && fourOfAKindBonus == -1)
                return 7000000 + fourOfAKindBonus * 100 + highCardBonus;
        }

        //Check for Full house
        int twoCardBonus = -1;
        int threeCardBonus = -1;
        for(int i = 13; i > 0; i--){
            int checkIndex = i;
            if(checkIndex == 13)
                checkIndex = 0;
            if(numOfValues[checkIndex] == 3 && threeCardBonus == -1)
                threeCardBonus = i;
            else if(numOfValues[checkIndex] == 2 && twoCardBonus == -1)
                twoCardBonus = i;
            if(twoCardBonus != -1 && threeCardBonus == -1)
                return 6000000 + threeCardBonus * 100 + twoCardBonus;
        }

        //Check for Flush
        for(int i = 0; i < 3; i++){
            if (hand.get(2-i)/13 == hand.get(6-i)/13){
                int handScore = 5000000 + hand.get(6-i) * 28561 + hand.get(5-i) * 2197 + hand.get(4-i) * 169 + hand.get(3-i) * 13 + hand.get(2-i);
                if(hand.contains((hand.get(6-i)/13)*13)){
                    handScore-=hand.get(2-i);
                    handScore+=371293;
                }
                return handScore;
            }
        }

        //Check for Straight
        for(int i = 0; i < 3; i++){
            if (handValue.get(i) == 0){
                for(int j = 0; j < 4; j++){
                    int esValue = hand.get(0);
                    if(handValue.contains(esValue+1) && handValue.contains(esValue+10) && handValue.contains(esValue+11) && handValue.contains(esValue+12)){
                        return 4000016;
                    }
                    else if(handValue.contains(esValue+1) && handValue.contains(esValue+2) && handValue.contains(esValue+11) && handValue.contains(esValue+12)){
                        return 4000015;
                    }
                    else if(handValue.contains(esValue+1) && handValue.contains(esValue+2) && handValue.contains(esValue+3) && handValue.contains(esValue+12)){
                        return 4000014;
                    }
                    else if(handValue.contains(esValue+1) && handValue.contains(esValue+2) && handValue.contains(esValue+3) && handValue.contains(esValue+4)){
                        return 4000013;
                    }
                }
            }
            if(handValue.get(i)+4==handValue.get(4+i)){
                return 4000000+hand.get(4+i)%13;
            }
        }

        //Check for Three of a kind
        threeCardBonus = -1;
        int firstHighCardBonus = -1;
        int secondHighCardBonus = -1;
        for(int i = 13; i > 0; i--){
            int checkIndex = i;
            if(checkIndex == 13)
                checkIndex = 0;
            if(numOfValues[checkIndex] == 3 && threeCardBonus == -1)
                threeCardBonus = i;
            else if(numOfValues[checkIndex] != 0 && firstHighCardBonus == -1)
                firstHighCardBonus = i;
            else if(numOfValues[checkIndex] != 0 && secondHighCardBonus == -1)
                secondHighCardBonus = i;

            if(firstHighCardBonus != -1 && secondHighCardBonus != -1 && threeCardBonus != -1)
                return 3000000 + threeCardBonus * 10000 + firstHighCardBonus * 100 + secondHighCardBonus;
        }

        //Check for Two pair
        int firstPairBonus = -1;
        int secondPairBonus = -1;
        highCardBonus = -1;
        for(int i = 13; i > 0; i--){
            int checkIndex = i;
            if(checkIndex == 13)
                checkIndex = 0;
            if(numOfValues[checkIndex] == 2 && firstPairBonus == -1)
                firstPairBonus = i;
            else if(numOfValues[checkIndex] == 2 && secondPairBonus == -1)
                secondPairBonus = i;
            else if(numOfValues[checkIndex] != 0 && highCardBonus == -1)
                highCardBonus = i;

            if(firstHighCardBonus != -1 && secondHighCardBonus != -1 && threeCardBonus != -1)
                return 2000000 + firstPairBonus * 10000 + secondPairBonus * 100 + highCardBonus;
        }

        //Check for One pair
        int pairBonus = -1;
        firstHighCardBonus = -1;
        secondHighCardBonus = -1;
        int thirdHighCardBonus = -1;
        for(int i = 13; i > 0; i--){
            int checkIndex = i;
            if(checkIndex == 13)
                checkIndex = 0;
            if(numOfValues[checkIndex] == 2 && pairBonus == -1)
                pairBonus = i;
            else if(numOfValues[checkIndex] == 1 && firstHighCardBonus == -1)
                firstHighCardBonus = i;
            else if(numOfValues[checkIndex] != 0 && secondHighCardBonus == -1)
                secondHighCardBonus = i;
            else if(numOfValues[checkIndex] != 0 && thirdHighCardBonus == -1)
                thirdHighCardBonus = i;

            if(pairBonus != -1 && firstHighCardBonus != -1 && secondHighCardBonus != -1 && thirdHighCardBonus != -1)
                return 1000000 + pairBonus * 10000 + firstHighCardBonus * 169 + secondHighCardBonus * 13 + thirdHighCardBonus;
        }

        //Check for High card
        return handValue.get(6) * 28561 + handValue.get(5) * 2197 + handValue.get(4) * 169 + handValue.get(3) * 13 + handValue.get(2);
    }
}
