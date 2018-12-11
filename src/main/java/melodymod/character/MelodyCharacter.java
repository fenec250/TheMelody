package melodymod.character;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import melodymod.cards.*;
import melodymod.patches.AbstractCardEnum;
import melodymod.patches.MelodyEnum;

import java.util.ArrayList;

public class MelodyCharacter extends CustomPlayer {
    public static final int ENERGY_PER_TURN = 3;
    public static final String MY_CHARACTER_SHOULDER_2 = "mysticmod/images/char/shoulder2.png";
    public static final String MY_CHARACTER_SHOULDER_1 = "mysticmod/images/char/shoulder.png";
    public static final String MY_CHARACTER_CORPSE = "mysticmod/images/char/corpse.png";
    public static final String MY_CHARACTER_ANIMATION = "mysticmod/images/char/idle/Animation.scml";
    private static final String ID = "melodymod:MelodyCharacter";
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;
    private static final Color melodyLime = CardHelper.getColor(191.0f, 255.0f, 1.0f); //0xbfff00
    private static final float DIALOG_X_ADJUSTMENT = 0.0F;
    private static final float DIALOG_Y_ADJUSTMENT = 220.0F;
    public static final String[] orbTextures = {
            "mysticmod/images/char/orb/layer1.png",
            "mysticmod/images/char/orb/layer2.png",
            "mysticmod/images/char/orb/layer3.png",
            "mysticmod/images/char/orb/layer4.png",
            "mysticmod/images/char/orb/layer5.png",
            "mysticmod/images/char/orb/layer6.png",
            "mysticmod/images/char/orb/layer1d.png",
            "mysticmod/images/char/orb/layer2d.png",
            "mysticmod/images/char/orb/layer3d.png",
            "mysticmod/images/char/orb/layer4d.png",
            "mysticmod/images/char/orb/layer5d.png"
    };

    public MelodyCharacter(String name) {
        super(name, MelodyEnum.MELODY_CLASS, orbTextures, "mysticmod/images/char/orb/vfx.png", null, new SpriterAnimation(MY_CHARACTER_ANIMATION));

        this.dialogX = this.drawX + DIALOG_X_ADJUSTMENT * Settings.scale;
        this.dialogY = this.drawY + DIALOG_Y_ADJUSTMENT * Settings.scale;

        initializeClass(null,
                MY_CHARACTER_SHOULDER_2,
                MY_CHARACTER_SHOULDER_1,
                MY_CHARACTER_CORPSE,
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
    }

    @Override
    public String getSpireHeartText() {
        return "NL You step towards the heart at the rythme of its pulses..."; //UPDATE BODY TEXT :(
    }

    @Override
    public Color getSlashAttackColor() {
        return melodyLime;
    }

    @Override
    public String getVampireText() {
        return "Navigating an unlit street, you come across several hooded figures in the midst of some dark ritual. As you approach, they turn to you in eerie unison. The tallest among them bares fanged teeth and extends a long, pale hand towards you. NL ~\"Join~ ~us~ ~wanderer,~ ~and~ ~feel~ ~the~ ~warmth~ ~of~ ~the~ ~Spire.\"~";
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.MELODY_LIME;
    }

    @Override
    public Color getCardRenderColor() {
        return melodyLime;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,
                AbstractGameAction.AttackEffect.POISON,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
                };
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new ObsidianStrike();
    }

    @Override
    public Color getCardTrailColor() {
        return melodyLime;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_FIRE", MathUtils.random(-0.2f, 0.2f));
        CardCrawlGame.sound.playA("ATTACK_FAST", MathUtils.random(-0.2f, 0.2f));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_FIRE";
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new MelodyCharacter(this.name);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(StrikeMelody.ID);
        retVal.add(StrikeMelody.ID);
        retVal.add(StrikeMelody.ID);
        retVal.add(StrikeMelody.ID);

        retVal.add(DefendMelody.ID);
        retVal.add(DefendMelody.ID);
        retVal.add(DefendMelody.ID);
        retVal.add(DefendMelody.ID);

        retVal.add(BeginnerSteps.ID);
        retVal.add(ObsidianStrike.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
//        retVal.add(SpellBook.ID);
//        UnlockTracker.markRelicAsSeen(SpellBook.ID);
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                77, 77, 0, 99, 5, //starting hp, max hp, max orbs, starting gold, starting hand size
                this, getStartingRelics(), getStartingDeck(), false);
    }
}