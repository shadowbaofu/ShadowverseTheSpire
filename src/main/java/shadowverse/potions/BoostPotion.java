package shadowverse.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import shadowverse.characters.AbstractShadowversePlayer;

public class BoostPotion extends CustomPotion {
    public static final String POTION_ID = "shadowverse:BoostPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("shadowverse:BoostPotion");
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public BoostPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.H, PotionColor.BLUE);
        this.labOutlineColor = CardHelper.getColor(46, 71, 81);
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
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.hasTag(AbstractShadowversePlayer.Enums.SPELL_BOOST)) {
                    for (int i = 0; i < this.potency; i++) {
                        c.flash();
                        addToBot(new SFXAction("spell_boost"));
                        addToBot(new ReduceCostAction(c));
                    }
                    continue;
                }
                if (c.hasTag(AbstractShadowversePlayer.Enums.SPELL_BOOST_ATTACK)) {
                    for (int i = 0; i < this.potency; i++) {
                        c.flash();
                        c.magicNumber = ++c.baseMagicNumber;
                        addToBot(new SFXAction("spell_boost"));
                    }
                }
            }
    }


    @Override
    public int getPotency(int i) {
        return 3;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BoostPotion();
    }
}
