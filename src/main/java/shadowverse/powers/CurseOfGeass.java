package shadowverse.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnPlayerDeathPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class CurseOfGeass extends AbstractPower implements OnPlayerDeathPower {
    public static final String POWER_ID = "shadowverse:CurseOfGeass";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:CurseOfGeass");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CurseOfGeass(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/CurseOfGeass.png");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {
        addToBot((AbstractGameAction)new SFXAction("Suzaku_CURSE"));
        addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner,this.owner,this));
        this.owner.heal(1, true);
        addToBot((AbstractGameAction)new RemoveDebuffsAction(this.owner));
        addToBot((AbstractGameAction)new GainBlockAction(this.owner,25));
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature) this.owner,(AbstractCreature) this.owner,(AbstractPower)new StrengthPower((AbstractCreature)this.owner,5),5));
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature) this.owner,(AbstractCreature) this.owner,(AbstractPower)new DexterityPower((AbstractCreature)this.owner,5),5));
        if (this.owner instanceof AbstractMonster){
            addToBot((AbstractGameAction)new PressEndTurnButtonAction());
        }
        return false;
    }
}
