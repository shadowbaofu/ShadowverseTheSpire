package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;

public class AzureBlast
        extends CustomCard {
    public static final String ID = "shadowverse:AzureBlast";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:AzureBlast");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/SonicFour.png";

    public AzureBlast() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Witchcraft.Enums.COLOR_BLUE, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        this.baseDamage = 8;
        this.tags.add(AbstractShadowversePlayer.Enums.MACHINE);
        this.exhaust = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("AzureBlast"));
        AbstractCreature m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (m != null) {
            for (int i = 0; i < 4; i++) {
                addToBot(new DamageAction(m, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
                if (Settings.FAST_MODE) {
                    addToBot(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, abstractPlayer.hb.cX, abstractPlayer.hb.cY), 0.1F));
                } else {
                    addToBot(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, abstractPlayer.hb.cX, abstractPlayer.hb.cY), 0.3F));
                }
                addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            }
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new AzureBlast();
    }
}

