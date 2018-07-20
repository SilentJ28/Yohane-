package yohanemod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import yohanemod.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class Livestream_Donation extends CustomCard{
    public static final String ID = "Livestream_Donation";
    public static final String NAME = "Livestream Donation";
    public static final String DESCRIPTION = "Gain !D! Fallen Energy. NL Draw a card for every !M! cards in your draw pile.";
    public static final String IMG_PATH = "cards/Livestream_Donation.png";
    private static final int COST = 1;
    private static final int FALLEN_ENERGY = 4;
    private static final int DRAW = 5;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Livestream_Donation() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.damage = this.baseDamage = FALLEN_ENERGY;
        this.magicNumber = this.baseMagicNumber = DRAW;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, this.damage), this.damage));
            AbstractDungeon.actionManager.addToBottom(new yohanemod.actions.Livestream_Donation(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Livestream_Donation();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(-1);
        }
    }

}
