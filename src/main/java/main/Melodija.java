package main;

import org.jfugue.Player;

public class Melodija {
    private Integer fitness;
    private String melodija;
    private static String result;

    public Melodija(String _melodija)
    {
        fitness = 0;
        melodija = _melodija;
        result = "E D C D E E E D D D E G G";
        setFitness();
    }
    public void sviraj()
    {
        Player player = new Player();
        player.play(melodija);
    }
    private void setFitness()
    {
        for(int i = 0; i < result.length(); i++)
        {
            if(melodija.charAt(i) != result.charAt(i))
            {
                fitness ++;
            }
        }
    }
    public Integer get_fitness()
    {
        return fitness;
    }
    public String get_melodija() {return melodija;}

    @Override
    public String toString() {
        return melodija +" fitness: "+ fitness +"\n";
    }
}
