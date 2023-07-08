package shadowverseCharbosses.cards.nemesis;

import shadowverseCharbosses.cards.AbstractBossCard;
import shadowverseCharbosses.vfx.EnemyRainbowCardEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;

public class EnSpineLucille extends AbstractBossCard {
    public static final String ID = "shadowverse:EnSpineLucille";

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:EnSpineLucille");

    public static final String IMG_PATH = "img/cards/SpineLucille.png";

    public EnSpineLucille() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.ATTACK, Nemesis.Enums.COLOR_SKY, CardRarity.RARE, CardTarget.ALL_ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 42;
        this.tags.add(AbstractShadowversePlayer.Enums.ARTIFACT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new EnemyRainbowCardEffect()));
        addToBot(new SFXAction("SpineLucille"));
        addToBot(new DamageAction(p, new DamageInfo(m, this.damage), AbstractGameAction.AttackEffect.FIRE));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(9);
        }
    }

    public AbstractCard makeCopy() {
        return (AbstractCard)new EnSpineLucille();
    }
}
