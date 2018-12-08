package melodymod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melodymod.patches.AbstractCardEnum;
import melodymod.patches.MelodyTags;
import melodymod.powers.RhythmePower;

public class Offbeat
        extends AbstractMelodyCard {
    public static final String ID = "melodymod:Offbeat";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "mysticmod/images/cards/strike.png";
    private static final int COST = 1;
    private static final int DAMAGE_AMT = 7;
    private static final int UPGRADE_DAMAGE_AMT = 3;
    private static final int TEMPO = 4;

    boolean tempoActive;

    public Offbeat() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.MELODY_LIME,
                CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = DAMAGE_AMT;
        this.tags.add(MelodyTags.IS_RYTHME);
        this.tempoActive = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.step(p);
        // try to consume Tempo
        if (!tempoActive && p.hasPower(RhythmePower.POWER_ID) && p.getPower(RhythmePower.POWER_ID).amount >= TEMPO) {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, RhythmePower.POWER_ID, TEMPO));
            tempoActive = true;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (tempoActive)
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

    }

    @Override
    public AbstractCard makeCopy() {
        // TODO: make the copy match tempoActive
        return new Offbeat();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE_AMT);
        }
    }
}