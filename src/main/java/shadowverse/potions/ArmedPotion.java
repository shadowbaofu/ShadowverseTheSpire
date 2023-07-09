package shadowverse.potions;

import basemod.abstracts.CustomPotion;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import shadowverse.cardmods.ArmedMod;
import shadowverse.powers.OverflowPower;

public class ArmedPotion extends CustomPotion {
    public static final String POTION_ID = "shadowverse:ArmedPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("shadowverse:ArmedPotion");
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString("shadowverse:applyEffect")).TEXT;


    public ArmedPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.ANVIL, PotionColor.ENERGY);
        this.labOutlineColor = CardHelper.getColor(117, 72, 29);
        this.isThrown = false;
    }

    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new GainBlockAction(AbstractDungeon.player,9));
            addToBot(new SelectCardsInHandAction(this.potency, TEXT[0], true, true, card -> {
                return CardLibrary.getCard(card.cardID)!= null && CardLibrary.getCard(card.cardID).type == AbstractCard.CardType.ATTACK;
            }, abstractCards -> {
                for (AbstractCard c : abstractCards) {
                    CardModifierManager.addModifier(c, new ArmedMod());
                    c.superFlash();
                }
            }));
        }
    }


    @Override
    public int getPotency(int i) {
        return 3;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ArmedPotion();
    }
}
