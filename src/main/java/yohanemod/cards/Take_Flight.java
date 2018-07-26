package yohanemod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FlightPlayerPower;

public class Take_Flight extends CustomCard {
    public static final String ID = "Take_Flight";
    public static final String NAME = "Take Flight!";
    public static final String DESCRIPTION = "Gain Flight. NL Lose !M! Fallen Energy at the end of your turn. Removed if Fallen Energy drops to 0.";
    public static final String IMG_PATH = "cards/Take_Flight.png";
    private static final int COST = 2;
    private static final int POOL = 1;
    private static final int FALLEN_ENERGY = 8;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;

    public Take_Flight() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, AbstractCardEnum.GREY,
                rarity, target, POOL);

        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FlightPlayerPower(p,3, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Take_Flight();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-2);
        }
    }
}
