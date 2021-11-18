package charbosses.cards.nemesis;

import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.HeartMegaDebuffEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import shadowverse.characters.Nemesis;

public class CalamityMode extends AbstractBossCard {
    public static final String ID = "shadowverse:CalamityMode";

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:CalamityMode");

    public static final String IMG_PATH = "img/cards/CalamityMode.png";

    public CalamityMode() {
        super(ID, cardStrings.NAME, IMG_PATH, 0, cardStrings.DESCRIPTION, CardType.SKILL, Nemesis.Enums.COLOR_SKY, CardRarity.RARE, CardTarget.SELF, AbstractMonster.Intent.MAGIC);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new SFXAction("CalamityMode"));
        addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new HeartMegaDebuffEffect()));
        addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new MiracleEffect(Color.BLACK,Color.WHITE.cpy(),"HEAL_3")));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
        }
    }

    public AbstractCard makeCopy() {
        return (AbstractCard)new CalamityMode();
    }
}
