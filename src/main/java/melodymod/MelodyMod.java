package melodymod;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.city.Healer;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import melodymod.cards.*;
import melodymod.character.MelodyCharacter;
import melodymod.patches.MelodyEnum;

import java.nio.charset.StandardCharsets;

import static melodymod.patches.AbstractCardEnum.MELODY_LIME;

@SpireInitializer
public class MelodyMod implements EditCardsSubscriber, EditCharactersSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, PostBattleSubscriber, PostInitializeSubscriber, OnStartBattleSubscriber {

    private static final Color melodyLime = CardHelper.getColor(191.0f, 255.0f, 1.0f); //0xbfff00
    private static final String attackCard = "mysticmod/images/512/bg_attack_mystic.png";
    private static final String skillCard = "mysticmod/images/512/bg_skill_mystic.png";
    private static final String powerCard = "mysticmod/images/512/bg_power_mystic.png";
    private static final String energyOrb = "mysticmod/images/512/card_mystic_orb.png";
    private static final String attackCardPortrait = "mysticmod/images/1024/bg_attack_mystic.png";
    private static final String skillCardPortrait = "mysticmod/images/1024/bg_skill_mystic.png";
    private static final String powerCardPortrait = "mysticmod/images/1024/bg_power_mystic.png";
    private static final String energyOrbPortrait = "mysticmod/images/1024/card_mystic_orb.png";
    private static final String charButton = "mysticmod/images/charSelect/button.png";
    private static final String charPortrait = "mysticmod/images/charSelect/portrait.png";
    private static final String miniManaSymbol = "mysticmod/images/manaSymbol.png";
    public static int numberOfTimesDeckShuffledThisCombat = 0;
    public static boolean healerAccused = false;
    public static Healer storedHealer;
    public static float storedHealerDialogY;
    public static ModPanel settingsPanel;


    public MelodyMod(){
        BaseMod.subscribe(this);

        BaseMod.addColor(MELODY_LIME,
                melodyLime, melodyLime, melodyLime, melodyLime, melodyLime, melodyLime, melodyLime,   //Background color, back color, frame color, frame outline color, description box color, glow color
                attackCard, skillCard, powerCard, energyOrb,                                                        //attack background image, skill background image, power background image, energy orb image
                attackCardPortrait, skillCardPortrait, powerCardPortrait, energyOrbPortrait,                        //as above, but for card inspect view
                miniManaSymbol);                                                                                    //appears in Mystic Purple cards where you type [E]
    }

    //Used by @SpireInitializer
    public static void initialize(){
        MelodyMod melodyMod = new MelodyMod();
    }

    @Override
    public void receivePostInitialize() {
        Texture badgeImg = new Texture("mysticmod/images/badge.png");
        settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeImg, "The Melody Mod", "Fenec250, Johnny Devo", "Adds a new character to the game: The Melody.", settingsPanel);
    }

    @Override
    public void receiveEditCards() {
        //secondMagicNumber dynamic variable
//        BaseMod.addDynamicVariable(new AbstractMysticCard.SecondMagicNumber());

        //Basic. 2 attacks, 2 skills
        BaseMod.addCard(new BeginnerSteps());
        BaseMod.addCard(new DefendMelody());
        BaseMod.addCard(new ObsidianStrike());
        BaseMod.addCard(new StrikeMelody());

        //Special
//        BaseMod.addCard(new BladeBurst());
//
        //Commons
        //10 attacks
        BaseMod.addCard(new Offbeat());
        BaseMod.addCard(new Clap());
        //8 skills
        BaseMod.addCard(new Crotchet());

        //Uncommons
        //11 attacks
        BaseMod.addCard(new Spin());
        //18 skills
        //6 powers

        //Rares.
        //4 attacks
        //8 skills
        //6 powers
        BaseMod.addCard(new BeatOfLife());
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new MelodyCharacter(CardCrawlGame.playerName), charButton, charPortrait, MelodyEnum.MELODY_CLASS);
    }

    @Override
    public void receiveEditKeywords() {
        String[] keywordRhythm = {"rhythm"};
        BaseMod.addKeyword(keywordRhythm, "Used to unlock Tempo effects. Decays by half each turn");
        String[] keywordTempo = {"tempo"};
        BaseMod.addKeyword(keywordTempo, "Comsume X Rhythm to unlock the associated effect for the rest of this combat.");
        String[] keywordGhost = {"ghost"};
        BaseMod.addKeyword(keywordGhost, "Exhaust, Ethereal, exhausts when discarded.");

//        String[] keywordCantrips = {"cantrip", "cantrips"};
//        BaseMod.addKeyword(keywordCantrips, "Considered a [#5299DC]Spell[] so long as you've played fewer than 3 [#5299DC]Spells[] this turn.");
    }

    @Override
    public void receiveEditStrings() {
        String cardStrings = Gdx.files.internal("melodymod/strings/cards.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String characterStrings = Gdx.files.internal("melodymod/strings/character.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CharacterStrings.class, characterStrings);
        String powerStrings = Gdx.files.internal("melodymod/strings/powers.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
//        String relicStrings = Gdx.files.internal("melodymod/strings/relics.json").readString(String.valueOf(StandardCharsets.UTF_8));
//        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
//        String uiStrings = Gdx.files.internal("melodymod/strings/ui.json").readString(String.valueOf(StandardCharsets.UTF_8));
//        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
    }

    @Override
    public void receiveEditRelics() {
        //starter
//        BaseMod.addRelicToCustomPool(new SpellBook(), MELODY_LIME);
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom room) {
        numberOfTimesDeckShuffledThisCombat = 0;
    }

    @Override
    public void receivePostBattle(final AbstractRoom p0) {
        numberOfTimesDeckShuffledThisCombat = 0;
        healerAccused = false;
        if (storedHealer != null) {
            storedHealer.dialogY = storedHealerDialogY;
            storedHealer = null;
        }
    }
}