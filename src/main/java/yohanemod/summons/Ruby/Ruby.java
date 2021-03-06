package yohanemod.summons.Ruby;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import kobting.friendlyminions.actions.ChooseAction;
import kobting.friendlyminions.actions.ChooseActionInfo;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

import java.util.ArrayList;

public class Ruby extends AbstractFriendlyMonster {
    public static String NAME = "Ruby";
    public static String ID = "Ruby";
    private ArrayList<ChooseActionInfo> moveInfo;
    private boolean hasAttacked = false;
    private AbstractMonster target;
    public int upgradeCount;
    public int[] multiDamage;
    public DamageInfo.DamageType damageTypeForTurn;
    public boolean isDamageModified = false;

    public Ruby(float offsetX) {
        super(NAME, ID, RubyNumbers.rubyHP,
                null, 15.0F, 10.0F, 230.0F, 240.0F, "summons/Ruby.png", offsetX, 0);
    }

    @Override
    public void applyStartOfTurnPowers() {
        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, this.currentBlock));
        System.out.println(this.name + " " + this.currentHealth);
    }

    @Override
    public void takeTurn() {
        if(!hasAttacked){
            moveInfo = makeMoves();
            ChooseAction pickAction = new ChooseAction(new RubyChoiceCards(), target, "Choose your attack");
            this.moveInfo.forEach( move -> {
                pickAction.add(move.getName(), move.getDescription(), move.getAction());
            });
            AbstractDungeon.actionManager.addToBottom(pickAction);
        }
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.hasAttacked = false;
    }

    private ArrayList<ChooseActionInfo> makeMoves() {
        ArrayList<ChooseActionInfo> tempInfo = new ArrayList<>();
        if (this.hasPower(RubyStrength.POWER_ID) && this.getPower(RubyStrength.POWER_ID).amount != 0) {
            upgradeCount = this.getPower(RubyStrength.POWER_ID).amount;
        }
        int attackDamage = (RubyNumbers.rubyAttackDamage + upgradeCount);
        int blockAmount = (RubyNumbers.rubyBlockAmount + upgradeCount);
        target = AbstractDungeon.getRandomMonster();
        String attackDesc = String.format("Deal %d damage to ALL enemies.", attackDamage);
        String blockDesc = String.format("Give %d Block to Yohane.", blockAmount);
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.id.equals(AwakenedOne.ID) || mo.id.equals(Darkling.ID)) {
                target = mo;
            }
        }
        if ((target != null)) {
            tempInfo.add(new ChooseActionInfo("Attack", attackDesc, () -> {
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    DamageInfo info = new DamageInfo(this, attackDamage, DamageInfo.DamageType.NORMAL);
                    info.applyPowers(mo, this);
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, info));
                }
            }));
        }
            tempInfo.add(new ChooseActionInfo("Defend", blockDesc, () -> {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, blockAmount));
            }));
            return tempInfo;
    }

    @Override
    protected void getMove(int i) {
    }

}
