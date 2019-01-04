package melodymod.cards;

import basemod.helpers.BaseModCardTags;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melodymod.patches.AbstractCardEnum;
import melodymod.patches.MelodyTags;
import melodymod.powers.RhythmPower;

public class ShiningStar
        extends AbstractMelodyCard {
    public static final String ID = "melodymod:ShiningStar";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "melodymod/images/cards/defend.png";
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    public ShiningStar() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.MELODY_LIME,
                CardRarity.BASIC, CardTarget.SELF);
        this.tags.add(BaseModCardTags.BASIC_DEFEND);
        this.tags.add(MelodyTags.IS_RYTHME);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.step(p);
        CardGroup dances = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        AbstractDungeon.player.drawPile.group.stream()
                .filter(c -> c.tags.contains(MelodyTags.IS_DANCE))
                .forEach(dances::addToRandomSpot);
        AbstractDungeon.actionManager.addToBottom(new FetchAction(dances));
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
//                p, p, new RhythmPower(p, this.magicNumber), this.magicNumber, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShiningStar();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }
}