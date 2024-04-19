package shadowverse.powers;


import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import shadowverse.cards.Neutral.Temp.AzureBlast;
import shadowverse.characters.AbstractShadowversePlayer;


public class Tetra4Power
        extends AbstractPower implements NonStackablePower {
    public static final String POWER_ID = "shadowverse:Tetra4Power";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("shadowverse:Tetra4Power");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int useCount;

    public Tetra4Power(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        updateDescription();
        this.img = new Texture("img/powers/SonicFourPower.png");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(AbstractShadowversePlayer.Enums.MACHINE)) {
            if (useCount < 3) {
                addToBot(new SFXAction("Tetra4Power"));
                useCount++;
                switch (useCount) {
                    case 1:
                        addToBot(new GainEnergyAction(1));
                        break;
                    case 2:
                        AbstractCreature m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                        if (m != null) {
                            addToBot(new DamageAction(m, new DamageInfo(this.owner, 8, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
                            if (Settings.FAST_MODE) {
                                addToBot(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, this.owner.hb.cX, this.owner.hb.cY), 0.1F));
                            } else {
                                addToBot(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, this.owner.hb.cX, this.owner.hb.cY), 0.3F));
                            }
                            addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
                        }
                        break;
                    case 3:
                        addToBot(new MakeTempCardInHandAction(new AzureBlast()));
                        break;
                }
            }
        }
    }

}


