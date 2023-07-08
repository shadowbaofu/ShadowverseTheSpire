package shadowverse.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import shadowverse.cards.Neutral.Temp.ForestBat;
import shadowverse.cards.Neutral.Temp.InfernoDragon;

public class BurnPotion extends CustomPotion {
    public static final String POTION_ID = "shadowverse:BurnPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("shadowverse:BurnPotion");
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public BurnPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.SPHERE, PotionColor.FIRE);
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
        Burn burn = new Burn();
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            addToBot(new MakeTempCardInHandAction(burn.makeStatEquivalentCopy(), 3));
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            addToBot(new MakeTempCardInHandAction(new InfernoDragon().makeStatEquivalentCopy(), this.potency));
    }



    @Override
    public int getPotency(int i) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BurnPotion();
    }
}
