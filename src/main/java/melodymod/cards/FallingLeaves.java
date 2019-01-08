package melodymod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Hologram;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melodymod.actions.DrawForEachStepAction;
import melodymod.patches.AbstractCardEnum;
import melodymod.patches.MelodyTags;

public class FallingLeaves
        extends AbstractMelodyCard {
    public static final String ID = "melodymod:FallingLeaves";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "melodymod/images/cards/defend.png";
    private static final int COST = 0;
    private static final int DANCE = 0;

    public FallingLeaves() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.MELODY_LIME,
                CardRarity.UNCOMMON, CardTarget.SELF);
        this.dance = this.DANCE;
        this.tags.add(MelodyTags.IS_DANCE);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.dance(p);
//        AbstractDungeon.actionManager.addToBottom(new RefundAction(this, DANCE_REFUND));
        AbstractDungeon.actionManager.addToBottom(new DrawForEachStepAction(p));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FallingLeaves();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
	        this.upgradeName();
            this.exhaust = false;
	        this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}