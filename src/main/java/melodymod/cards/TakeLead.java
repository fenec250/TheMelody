package melodymod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import melodymod.patches.AbstractCardEnum;
import melodymod.patches.MelodyTags;

public class TakeLead extends AbstractMelodyCard {
    public static final String ID = "melodymod:TakeLead";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "melodymod/images/cards/defend.png";
    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;
    private static final int DANCE = 3;
    private static final int FETCH_AMOUNT = 1;

    public TakeLead() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.MELODY_LIME,
                CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = FETCH_AMOUNT;
        this.dance = DANCE;
        this.tags.add(MelodyTags.IS_DANCE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new FetchAction(AbstractDungeon.player.drawPile,
                        (a) -> a.tags.contains(MelodyTags.IS_DANCE), this.magicNumber));
        this.dance(p);
    }

    @Override
    public AbstractCard makeCopy() {
        return new TakeLead();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeBaseCost(UPGRADED_COST);
            this.upgradeName();
        }
    }
}