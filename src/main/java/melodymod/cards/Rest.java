package melodymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import melodymod.patches.AbstractCardEnum;
import melodymod.patches.MelodyTags;
import melodymod.powers.RhythmPower;

public class Rest
        extends AbstractMelodyCard {
    public static final String ID = "melodymod:Rest";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "mysticmod/images/cards/defend.png";
    private static final int COST = 1;
    private static final int BLOCK_AMT = 7;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int TEMPO  = 2;
    private static final int TEMPO_BLOCK = 4;
    private static final int UPGRADE_TEMPO_BLOCK = 2;

    boolean tempoActive;

    public Rest() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.MELODY_LIME,
                CardRarity.COMMON, CardTarget.SELF);
        this.block = this.baseBlock = BLOCK_AMT;
        this.magicNumber = this.baseMagicNumber = TEMPO_BLOCK;
        this.tags.add(MelodyTags.IS_TEMPO);
        this.tempoActive = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.step(p);
        // try to consume Tempo
        if (!tempoActive && p.hasPower(RhythmPower.POWER_ID) && p.getPower(RhythmPower.POWER_ID).amount >= TEMPO) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, RhythmPower.POWER_ID, TEMPO));
            tempoActive = true;
        }
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if (tempoActive)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        // TODO: make the copy match tempoActive
        return new Rest();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
            this.upgradeMagicNumber(UPGRADE_TEMPO_BLOCK);
        }
    }
}