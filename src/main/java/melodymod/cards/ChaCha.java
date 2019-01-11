package melodymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melodymod.patches.AbstractCardEnum;
import melodymod.patches.MelodyTags;
import melodymod.powers.DancePower;

public class ChaCha
        extends AbstractMelodyCard {
    public static final String ID = "melodymod:ChaCha";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "melodymod/images/cards/strike.png";
    private static final int COST = 3;
    private static final int DANCE = 3;

    public ChaCha() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.MELODY_LIME,
                CardRarity.RARE, CardTarget.SELF);
        this.dance = this.DANCE;
        this.tags.add(MelodyTags.IS_DANCE);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard step1 = new TripleStep();
        AbstractCard step2 = new SideStep();
        AbstractCard step3 = new RockStep();
        if (this.upgraded) {
            step1.upgrade();
            step2.upgrade();
            step3.upgrade();
        }
//        step1.rawDescription = EXTENDED_DESCRIPTION[0] + EXTENDED_DESCRIPTION[1];
        initializeDescription();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(step1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(step2));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(step3));

        this.dance(p);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ChaCha();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
        // could track multi-upgrade and apply them to the generated cards?
    }
}