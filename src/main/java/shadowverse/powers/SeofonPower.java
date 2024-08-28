package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SeofonPower extends AbstractPower {
        public static final String POWER_ID = "shadowverse:SeofonPower";
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:SeofonPower");
        public static final String NAME = powerStrings.NAME;
        public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SeofonPower(AbstractCreature owner) {
            this.name = NAME;
            this.ID = POWER_ID;
            this.owner = owner;
            this.amount = -1;
            this.type = AbstractPower.PowerType.BUFF;
            updateDescription();
            this.img = new Texture("img/powers/SeofonPower.png");
        }


        public void updateDescription() {
            this.description = DESCRIPTIONS[0];
        }
    }
