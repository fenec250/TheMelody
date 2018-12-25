package melodymod.cards;

import basemod.helpers.BaseModCardTags;
import com.evacipated.cardcrawl.mod.stslib.actions.common.RefundAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melodymod.patches.AbstractCardEnum;
import melodymod.patches.MelodyTags;
import melodymod.powers.StepPower;

public class BeginnerSteps
        extends AbstractMelodyCard {
    public static final String ID = "melodymod:BeginnerSteps";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "mysticmod/images/cards/defend.png";
    private static final int COST = 1;
    private static final int DRAW = 1;
    private static final int DANCE_DRAW = 1;
    private static final int DANCE_REFUND = 1;
    private static final int DANCE = 1;
    private static final int UPGRADE_DANCE = 4;

    public BeginnerSteps() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.MELODY_LIME,
                CardRarity.BASIC, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = DRAW;
        this.tags.add(BaseModCardTags.BASIC_DEFEND);
        this.tags.add(MelodyTags.IS_DANCE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.step(p);
        if (p.hasPower(StepPower.POWER_ID) &&
                (p.getPower(StepPower.POWER_ID).amount == DANCE)
                || (this.upgraded && p.getPower(StepPower.POWER_ID).amount == UPGRADE_DANCE)) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DANCE_DRAW));
            AbstractDungeon.actionManager.addToBottom(new RefundAction(this, DANCE_REFUND));
        }
//        else {
//            RefundFields.refund.set(this, 0);
//        }

        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BeginnerSteps();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.upgradeName();
        }
    }
}