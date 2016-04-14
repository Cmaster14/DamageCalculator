package edu.mcm0046auburn.damagecalc;

/**
 * Created by TheRiddler on 4/12/16.
 */

public class Pokemon {
    static String name;
    int hp, atk, def, spatk, spdef, spe, level;
    int ivhp = 0, ivatk = 0, ivdef = 0, ivspatk = 0, ivspdef = 0, ivspe = 0;
    int evhp = 0, evatk = 0, evdef = 0, evspatk = 0, evspdef = 0, evspe = 0;
    static String type1, type2;
    static String nature;
    boolean hasIVs = false;

    Pokemon(String _name, int _hp, int _atk, int _def, int _spatk, int _spdef, int _spe, int _level, String _type1, String _type2, String _nature)
    {
        //these stats that are put in are the base stats of the pokemon
        name = _name;
        hp = _hp;
        atk = _atk;
        def = _def;
        spatk = _spatk;
        spdef = _spdef;
        spe =  _spe;
        level = _level;
        type1 = _type1;
        type2 = _type2;
        nature = _nature;
    }

    public Pokemon ConvertStats(Pokemon mon, boolean ivsSet){
        if(ivsSet)
        {
            //most stats:  (((IV + 2 * BaseStat + (EV/4) ) * Level/100 ) + 5) * Nature Value
            mon.atk = (((mon.ivatk + 2 * mon.atk + (mon.evatk / 4)) * mon.level / 100) + 5);
            mon.def = (((mon.ivdef + 2 * mon.def + (mon.evdef / 4)) * mon.level / 100) + 5);
            mon.spatk = (((mon.ivspatk + 2 * mon.spatk + (mon.evspatk / 4)) * mon.level / 100) + 5);
            mon.spdef = (((mon.ivspdef + 2 * mon.spdef + (mon.evspdef / 4)) * mon.level / 100) + 5);
            mon.spe = (((mon.ivspe + 2 * mon.spe + (mon.evspe / 4)) * mon.level / 100) + 5);

            mon = natureMod(mon);

            //hp stat:  ( (IV + 2 * BaseStat + (EV/4) ) * Level/100 ) + 10 + Level
            mon.hp = ((mon.ivhp + 2 * mon.hp + (mon.evhp / 4)) * mon.level / 100) + 10 + mon.level;
        }
        return mon;
    }

    private Pokemon natureMod(Pokemon mon)
    {
        switch(mon.nature){
            case "bold":
                mon.def *= 1.1;
                mon.atk *= 0.9;
                break;
            case "modest":
                mon.spatk *= 1.1;
                mon.atk *= 0.9;
                break;
            case "calm":
                mon.spdef *= 1.1;
                mon.atk *= 0.9;
                break;
            case "timid":
                mon.spe *= 1.1;
                mon.atk *= 0.9;
                break;
            case "lonely":
                mon.atk *= 1.1;
                mon.def *= 0.9;
                break;
            case "mild":
                mon.spatk *= 1.1;
                mon.def *= 0.9;
                break;
            case "gentle":
                mon.spdef *= 1.1;
                mon.def *= 0.9;
                break;
            case "hasty":
                mon.spe *= 1.1;
                mon.def *= 0.9;
                break;
            case "adamant":
                mon.atk *= 1.1;
                mon.spatk *= 0.9;
                break;
            case "impish":
                mon.def *= 1.1;
                mon.spatk *= 0.9;
                break;
            case "careful":
                mon.spdef *= 1.1;
                mon.spatk *= 0.9;
                break;
            case "jolly":
                mon.spe *= 1.1;
                mon.spatk *= 0.9;
                break;
            case "naughty":
                mon.atk *= 1.1;
                mon.spdef *= 0.9;
                break;
            case "lax":
                mon.def *= 1.1;
                mon.spdef *= 0.9;
                break;
            case "rash":
                mon.spatk *= 1.1;
                mon.spdef *= 0.9;
                break;
            case "naive":
                mon.spe *= 1.1;
                mon.spdef *= 0.9;
                break;
            case "brave":
                mon.atk *= 1.1;
                mon.spe *= 0.9;
                break;
            case "relaxed":
                mon.def *= 1.1;
                mon.spe *= 0.9;
                break;
            case "quiet":
                mon.spatk *= 1.1;
                mon.spe *= 0.9;
                break;
            case "sassy":
                mon.spdef *= 1.1;
                mon.spe *= 0.9;
                break;
            default: break;
        }
        return mon;
    }

    public void ivSet(Pokemon mon, int hp, int atk, int def, int spatk, int spdef, int spe)
    {
        //This sets the Individual Values of the Pokemon
        mon.ivhp = hp;
        mon.ivatk = atk;
        mon.ivdef = def;
        mon.ivspatk = spatk;
        mon.ivspdef = spdef;
        mon.ivspe = spe;

        hasIVs = true;
    }

    public void evSet(Pokemon mon, int hp, int atk, int def, int spatk, int spdef, int spe)
    {
        //This sets the Effort Values of the pokemon

        int sum = hp+atk+def+spatk+spdef+spe;
        //we want to check to make sure that the values provided don't add up past 510
        if(sum <= 510 && sum >= 0)
        {
            mon.evhp = hp;
            mon.evatk = atk;
            mon.evdef = def;
            mon.evspatk = spatk;
            mon.evspdef = spdef;
            mon.evspe = spe;
        }
    }
}
