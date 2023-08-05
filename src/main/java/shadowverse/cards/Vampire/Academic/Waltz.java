package shadowverse.cards.Vampire.Academic;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.Vampire;
import shadowverse.powers.ExitVengeancePower;
import shadowverse.stance.Vengeance;

public class Waltz
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Waltz";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Waltz");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Waltz.png";
    public static final String IMG_PATH_EV = "img/cards/Waltz_Ev.png";

    public Waltz() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Vampire.Enums.COLOR_SCARLET, CardRarity.RARE, CardTarget.ENEMY, 0, CardType.SKILL);
        this.baseDamage = 6;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(5);
            this.textureImg = IMG_PATH_EV;
            this.loadCardImage(IMG_PATH_EV);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Waltz"));
        addToBot(new ChangeStanceAction(new Vengeance()));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ExitVengeancePower(AbstractDungeon.player, 999)));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        if (m != null)
            addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.SCARLET, Color.RED), 0.2F));
        if (this.upgraded) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
            if (m != null)
                addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.SCARLET, Color.RED), 0.2F));
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
            if (m != null)
                addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.SCARLET, Color.RED), 0.2F));
        }
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("Waltz_Acc"));
        addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, 1), 1));
        addToBot(new ChangeStanceAction(new Vengeance()));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ExitVengeancePower(AbstractDungeon.player, 2)));
    }


    public AbstractCard makeCopy() {
        return new Waltz();
    }
}

