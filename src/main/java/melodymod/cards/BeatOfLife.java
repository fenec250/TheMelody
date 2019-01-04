package melodymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melodymod.patches.AbstractCardEnum;
import melodymod.powers.BeatOfLifePower;

public class BeatOfLife
        extends AbstractMelodyCard {
    public static final String ID = "melodymod:BeatOfLife";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "melodymod/images/cards/strike.png";
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    private static final int BLOCK_AMT = 1;

    public BeatOfLife() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, AbstractCardEnum.MELODY_LIME,
                CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = BLOCK_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                p, p, new BeatOfLifePower(p, this.magicNumber), this.magicNumber, true));
        this.step(p);
    }

    @Override
    public AbstractCard makeCopy() {
        return new BeatOfLife();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.cost = UPGRADED_COST;
            this.upgradeName();
        }
    }
}