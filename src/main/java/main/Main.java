package main;

import org.jfugue.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    public final static Integer MELODIJA_LEN = 25;
    public final static Integer GEN_SIZE = 12;
    public static final Random random = new Random();
    public static final Integer chanceToMutate = 3;
    public static final Integer popSize = GEN_SIZE;

    public static void main(String[] args)
    {
        Player play = new Player();
        long startTime = System.nanoTime();
        List<Melodija> generacija = new ArrayList<>(giveMeGeneration());
        generacija.sort((bs1, bs2) -> bs1.get_fitness().compareTo(bs2.get_fitness()));
        recombination(generacija.get(0), generacija.get(1));
        System.out.println(generacija);
        int count = 0;
        for(;;)
        {
            generacija = kTurnir(generacija);
            generacija.sort((_bs1, _bs2) -> _bs1.get_fitness().compareTo(_bs2.get_fitness()));
            count++;
            System.out.println("new gen no: " + count);
            if(count % 5 == 0)
            {
                play.play(generacija.get(0).get_melodija());
            }
            if(count == 4000)
            {
                break;
            }
            System.out.print(generacija.get(0).get_fitness() + ",");
            if(generacija.get(0).get_fitness().equals(0))
                break;
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;
        play.play(generacija.get(0).get_melodija());
        System.out.print("FINAL GEN\n" + generacija);
        System.out.println("FINAL GEN NUMBER:" + count);
        System.out.println("TIME TO EXECUTE: " + duration + "ms");
    }

    public static Melodija tournament(List<Melodija> allMellodies){
        List<Melodija> bsCpy = new ArrayList<>(allMellodies);

        int rand1 = random.nextInt(popSize);

        Melodija r1 = bsCpy.get(rand1);
        bsCpy.remove(rand1);
        int rand2 = random.nextInt(popSize-1);
        Melodija r2 = bsCpy.get(rand2);
        bsCpy.remove(rand2);
        int rand3 = random.nextInt(popSize-2);
        Melodija r3 = bsCpy.get(rand3);
        bsCpy.remove(rand3);

        List<Melodija> kandidati = new ArrayList<>();
        kandidati.add(r1);
        kandidati.add(r2);
        kandidati.add(r3);
        kandidati.sort((_r1, _r2) -> _r1.get_fitness().compareTo(_r2.get_fitness()));
        return kandidati.get(0);
    }

    public static List<Melodija> kTurnir(List<Melodija> boardStates)
    {
        List<Melodija> newGen = new ArrayList<>();

        for(int i = 0; i < popSize/2; i++)
        {
            List<Melodija> bsCpy = new ArrayList<>(boardStates);
            Melodija r1 = tournament(bsCpy);
            Melodija r2;
            do{
                r2 = tournament(bsCpy);
            }while ((r1.get_melodija()).equals(r2.get_melodija()));

            List<Melodija> child = new ArrayList<>(recombination(r1, r2));
            //System.out.println("Parents: \n"+r1 +" " +r2 +"Childs: \n" + child);
            newGen.add(child.get(0));
            newGen.add(child.get(1));
            boardStates.add(child.get(0));
            boardStates.add(child.get(1));
        }
        //boardStates.sort((bs1, bs2) -> bs1.get_fitness().compareTo(bs2.get_fitness()));
        //boardStates.subList(popSize,popSize*2).clear();
        return newGen;
    }


    private static String giveMeRandString()
    {
        RandomString r1 = new RandomString();
        String radnSt = r1.getAlphaNumericString(MELODIJA_LEN);
        return radnSt;
    }
    private static List<Melodija> giveMeGeneration()
    {
        List<Melodija> generacija = new ArrayList<>();
        for(int i = 0; i < GEN_SIZE; i++)
        {
            generacija.add(new Melodija(giveMeRandString()));
        }
        return generacija;
    }

    public static List<Melodija> recombination(Melodija mel1, Melodija mel2)
    {
        RandomString randomChar = new RandomString();
        List<Character> r1 = new ArrayList<>();
        List<Character> r2 = new ArrayList<>();
        Integer nRekomb = random.nextInt(MELODIJA_LEN);
        for(int i = 0; i < nRekomb; i++)
        {
            r1.add(mel1.get_melodija().charAt(i));
            r2.add(mel2.get_melodija().charAt(i));
        }
        for(int i = nRekomb; i < mel1.get_melodija().length(); i++)
        {
            r1.add(mel2.get_melodija().charAt(i));
            r2.add(mel1.get_melodija().charAt(i));
        }
        String r1_s = new String();
        String r2_s = new String();
        int mut = 1;
        while (mut % 2 != 0) //ZBOG PRAZNIH MJESTA U STRINGU (A D D ...)
        {
            mut = random.nextInt(100);
        }
        if(mut <= chanceToMutate)
        {
            int mutGen = 1;
            while (mutGen % 2 != 0)
            {
               mutGen = random.nextInt(MELODIJA_LEN);
            }
            r1.set(mutGen, randomChar.getAlphaNumericChar(1));//RANDOM NUMBER
        }
        mut = 1;
        while (mut % 2 != 0) //ZBOG PRAZNIH MJESTA U STRINGU (A D D ...)
        {
            mut = random.nextInt(100);
        }
        if(mut <= chanceToMutate)
        {
            int mutGen = 1;

            while (mutGen % 2 != 0)
            {
                mutGen = random.nextInt(MELODIJA_LEN);
            }
            r2.set(mutGen, randomChar.getAlphaNumericChar(1));
        }
        r1_s = r1.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
        r2_s = r2.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());

        List<Melodija> comb = new ArrayList<>();
        Melodija mel1_fin = new Melodija(r1_s);
        Melodija mel2_fin = new Melodija(r2_s);
        comb.add(mel1_fin);
        comb.add(mel2_fin);
        return comb;
    }
}
