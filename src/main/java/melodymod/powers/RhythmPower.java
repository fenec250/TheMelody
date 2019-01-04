package melodymod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RhythmPower extends AbstractPower {
    public static final String POWER_ID = "melodymod:RhythmPower";
    public static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTIONS = cardStrings.DESCRIPTIONS;

//    boolean decay;
//
    public RhythmPower(AbstractCreature owner, int initialAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.region128 = new TextureAtlas.AtlasRegion(new Texture("mysticmod/images/powers/ebb power 84.png"), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture("mysticmod/images/powers/ebb power 32.png"), 0, 0, 32, 32);
        this.type = PowerType.BUFF;
        this.amount = initialAmount;
        this.updateDescription();

//        this.decay = false;
//
    }

    @Override
    public void updateDescription() {
//        if (this.decay)
//            description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + this.amount/2;
        description = DESCRIPTIONS[0];
    }
//
//    @Override
//    public void atStartOfTurn() {
//        decay = true;
        // if we can gain Rhythme during opponents turns we should do that at the same time as the Decay.
//        this.flashWithoutSound();
//    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flashWithoutSound();
//        if (this.decay)
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(
                this.owner, this.owner, this, this.amount/2));
    }

//            this.decay = false;
}
