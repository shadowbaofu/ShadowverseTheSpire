package shadowverse.cards.Neutral.Temp;


import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.WallopAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;


public class Antemaria_Token extends CustomCard {
    public static final String ID = "shadowverse:Antemaria_Token";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Antemaria_Token");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Antemaria.png";

    public Antemaria_Token() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 12;
        this.tags.add(AbstractShadowversePlayer.Enums.CONDEMNED);
        this.exhaust = true;
        this.isEthereal = true;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("Antemaria"));
        addToBot(new VFXAction(new ViolentAttackEffect(abstractMonster.hb.cX, abstractMonster.hb.cY, Color.ORANGE), 0.8F));
        addToBot(new WallopAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn)));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Antemaria_Token();
    }
}

