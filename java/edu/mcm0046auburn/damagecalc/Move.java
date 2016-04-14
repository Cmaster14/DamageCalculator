package edu.mcm0046auburn.damagecalc;

/**
 * Created by TheRiddler on 4/13/16.
 */


public class Move {
    static String type;
    static int power;
    static int contact; // This determines whether a move is physical (0) or special (1)

    Move(String _type, int _power)
    {
        type = _type;
        power = _power;
    }

    // We return a string of the damage possibilities for the damage dealt.
    public String Damage(Pokemon mon, Move move, Pokemon def, boolean crit)
    {
        String damage;
        double modifier, randLow = 0.85, randHigh= 1.0;
        double nRDLow = 0, nRDHigh = 0; //non-Rounded Damage low and high
        int rDLow, rDHigh; //Rounded damage low and high

        // Damage = ( ((2*Level+10)/250) * (Attack/Defense) * Base + 2) * Modifier
        // where Modifier = STAB * Type * Critical * other * (random # from [.85, 1])
        // the result is rounded down and remainders are not kept
        // Pokemon.ConvertStats() accounts for nature
        // Move.Effective() accounts for type and STAB

        mon = mon.ConvertStats(mon, mon.hasIVs);
        def = def.ConvertStats(def, def.hasIVs);

        // This calculates the modifier based on STAB and Type of move and both Pokemon
        modifier = Effective(mon, move, def);
        if (crit)
        {
            modifier *= 1.5;
        }

        // This if/elseif statement calculates the range of non-rounded damage of the move used based on physical or special attack and defense
        if (move.contact == 0)
        {
            nRDLow = (((2 * mon.level + 10) / 250) * (mon.atk/ def.def) * move.power + 2) * modifier * 0.85;
            nRDHigh = (((2 * mon.level + 10) / 250) * (mon.atk/ def.def) * move.power + 2) * modifier * 1.0;
        }
        else if (move.contact ==1)
        {
            nRDLow = (((2 * mon.level + 10) / 250) * (mon.spatk/ def.spdef) * move.power + 2) * modifier * 0.85;
            nRDHigh = (((2 * mon.level + 10) / 250) * (mon.spatk/ def.spdef) * move.power + 2) * modifier * 1.0;
        }

        rDLow = (int) nRDLow;
        rDHigh = (int) nRDHigh;

        damage = rDLow + "-" + rDHigh;
        return damage;
    }

    public double Effective(Pokemon mon, Move move, Pokemon def)
    {
        double multiplier = 1;
        String[] defTypes = {def.type1, def.type2};
        int i = 0;

        //defenderT2 will be identical to defenderT1 in the case of a single typed defender
        //so we check to see if the type is identical.
        boolean singleType = false;
        if (def.type1.equals(def.type2))
        {
            singleType = true;
        }

        //OR we could do this...
        switch (move.type){
            case "fire":
                do {
                    switch (defTypes[i]){
                        case "fire": multiplier *= 0.5;
                            break;
                        case "water": multiplier *= 0.5;
                            break;
                        case "grass": multiplier *= 2;
                            break;
                        case "steel": multiplier *= 2;
                            break;
                        case "rock": multiplier *= 0.5;
                            break;
                        case "bug": multiplier *= 2;
                            break;
                        case "dragon": multiplier *= 0.5;
                            break;
                        case "ice": multiplier *= 2;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "water":
                do {
                    switch (defTypes[i]){
                        case "fire": multiplier *= 2;
                            break;
                        case "water": multiplier *= 0.5;
                            break;
                        case "grass": multiplier *= 0.5;
                            break;
                        case "rock": multiplier *= 2;
                            break;
                        case "ground": multiplier *= 2;
                            break;
                        case "dragon": multiplier *= 0.5;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "grass":
                do {
                    switch (defTypes[i]){
                        case "fire": multiplier *= 0.5;
                            break;
                        case "water": multiplier *= 2;
                            break;
                        case "grass": multiplier *= 0.5 ;
                            break;
                        case "steel": multiplier *= 0.5;
                            break;
                        case "rock": multiplier *= 2;
                            break;
                        case "ground": multiplier *= 2;
                            break;
                        case "bug": multiplier *= 0.5;
                            break;
                        case "poison": multiplier *= 0.5;
                            break;
                        case "dragon": multiplier *= 0.5;
                            break;
                        case "flying": multiplier *= 0.5;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "dark":
                do {
                    switch (defTypes[i]){
                        case "dark": multiplier *= 0.5;
                            break;
                        case "psychic": multiplier *= 2;
                            break;
                        case "fairy": multiplier *= 0.5;
                            break;
                        case "ghost": multiplier *= 2;
                            break;
                        case "fighting": multiplier *= 0.5;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "electric":
                do {
                    switch (defTypes[i]){
                        case "water": multiplier *= 2;
                            break;
                        case "grass": multiplier *= 0.5;
                            break;
                        case "electric": multiplier *= 0.5;
                            break;
                        case "ground": multiplier *= 0;
                            break;
                        case "dragon": multiplier *= 0.5;
                            break;
                        case "flying": multiplier *= 2;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "normal":
                do {
                    switch (defTypes[i]){
                        case "steel": multiplier *= 0.5;
                            break;
                        case "rock": multiplier *= 0.5;
                            break;
                        case "ghost": multiplier *= 0;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "steel":
                do {
                    switch (defTypes[i]){
                        case "fire": multiplier *= 0.5;
                            break;
                        case "water": multiplier *= 0.5;
                            break;
                        case "electric": multiplier *= 0.5;
                            break;
                        case "steel": multiplier *= 0.5;
                            break;
                        case "rock": multiplier *= 2;
                            break;
                        case "fairy": multiplier *= 2;
                            break;
                        case "ice": multiplier *= 2;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "rock":
                do {
                    switch (defTypes[i]){
                        case "fire": multiplier *= 2;
                            break;
                        case "steel": multiplier *= 0.5;
                            break;
                        case "ground": multiplier *= 0.5;
                            break;
                        case "bug": multiplier *= 2;
                            break;
                        case "ice": multiplier *= 2;
                            break;
                        case "flying": multiplier *= 2;
                            break;
                        case "fighting": multiplier *= 0.5;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "ground":
                do {
                    switch (defTypes[i]){
                        case "fire": multiplier *= 2;
                            break;
                        case "grass": multiplier *= 0.5;
                            break;
                        case "electric": multiplier *= 2;
                            break;
                        case "steel": multiplier *= 2;
                            break;
                        case "rock": multiplier *= 2;
                            break;
                        case "bug": multiplier *= 0.5;
                            break;
                        case "flying": multiplier *= 0;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "bug":
                do {
                    switch (defTypes[i]){
                        case "fire": multiplier *= 0.5;
                            break;
                        case "grass": multiplier *= 2;
                            break;
                        case "dark": multiplier *= 2;
                            break;
                        case "steel": multiplier *= 0.5;
                            break;
                        case "psychic": multiplier *= 2;
                            break;
                        case "poison": multiplier *= 0.5;
                            break;
                        case "fairy": multiplier *= 0.5;
                            break;
                        case "ghost": multiplier *= 0.5;
                            break;
                        case "flying": multiplier *= 0.5;
                            break;
                        case "fighting": multiplier *= 0.5;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "psychic":
                do {
                    switch (defTypes[i]){
                        case "dark": multiplier *= 0;
                            break;
                        case "steel": multiplier *= 0.5;
                            break;
                        case "psychic": multiplier *= 0.5;
                            break;
                        case "poison": multiplier *= 2;
                            break;
                        case "fighting": multiplier *= 2;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "poison":
                do {
                    switch (defTypes[i]){
                        case "grass": multiplier *= 2;
                            break;
                        case "steel": multiplier *= 0;
                            break;
                        case "rock": multiplier *= 0.5;
                            break;
                        case "ground": multiplier *= 0.5;
                            break;
                        case "poison": multiplier *= 0.5;
                            break;
                        case "fairy": multiplier *= 2;
                            break;
                        case "ghost": multiplier *= 0.5;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "fairy":
                do {
                    switch (defTypes[i]){
                        case "fire": multiplier *= 0.5;
                            break;
                        case "dark": multiplier *= 2;
                            break;
                        case "steel": multiplier *= 0.5;
                            break;
                        case "poison": multiplier *= 0.5;
                            break;
                        case "fighting": multiplier *= 2;
                            break;
                        case "dragon": multiplier *= 2;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "ghost":
                do {
                    switch (defTypes[i]){
                        case "fire": multiplier *= 0.5;
                            break;
                        case "steel": multiplier *= 0.5;
                            break;
                        case "psychic": multiplier *= 2;
                            break;
                        case "poison": multiplier *= 0.5;
                            break;
                        case "ghost": multiplier *= 2;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "dragon":
                do {
                    switch (defTypes[i]){
                        case "steel": multiplier *= 0.5;
                            break;
                        case "fairy": multiplier *= 0;
                            break;
                        case "dragon": multiplier *= 2;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "ice":
                do {
                    switch (defTypes[i]){
                        case "fire": multiplier *= 0.5;
                            break;
                        case "water": multiplier *= 0.5;
                            break;
                        case "grass": multiplier *= 2;
                            break;
                        case "steel": multiplier *= 0.5;
                            break;
                        case "ground": multiplier *= 2;
                            break;
                        case "dragon": multiplier *= 2;
                            break;
                        case "ice": multiplier *= 0.5;
                            break;
                        case "flying": multiplier *= 2;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "flying":
                do {
                    switch (defTypes[i]){
                        case "grass": multiplier *= 2;
                            break;
                        case "electric": multiplier *= 0.5;
                            break;
                        case "steel": multiplier *= 0.5;
                            break;
                        case "rock": multiplier *= 0.5;
                            break;
                        case "bug": multiplier *= 2;
                            break;
                        case "fighting": multiplier *= 2;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
            case "fighting":
                do {
                    switch (defTypes[i]){
                        case "normal": multiplier *= 2;
                            break;
                        case "steel": multiplier *= 2;
                            break;
                        case "rock": multiplier *= 2;
                            break;
                        case "bug": multiplier *= 0.5;
                            break;
                        case "psychic": multiplier *= 0.5;
                            break;
                        case "poison": multiplier *= 0.5;
                            break;
                        case "fairy": multiplier *= 0.5;
                            break;
                        case "ghost": multiplier *= 0;
                            break;
                        case "ice": multiplier *= 2;
                            break;
                        case "flying": multiplier *= 0.5;
                            break;
                        default: break;
                    }
                    i +=1;
                }while(!singleType && i < defTypes.length);
                break;
        }

        //here we check if the user's type matches the move's type for STAB calculation
        if (mon.type1.equals(move.type) || mon.type2.equals(move.type))
        {
            multiplier *= 1.5;
        }
        return multiplier;
    }
}
