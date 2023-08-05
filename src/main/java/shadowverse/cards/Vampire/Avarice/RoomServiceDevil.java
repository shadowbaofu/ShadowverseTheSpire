package shadowverse.cards.Vampire.Avarice;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import shadowverse.Shadowverse;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Vampire;

public class RoomServiceDevil
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:RoomServiceDevil";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:RoomServiceDevil");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/RoomServiceDevil.png";

    public RoomServiceDevil() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Vampire.Enums.COLOR_SCARLET, CardRarity.COMMON, CardTarget.ENEMY, 0, CardType.SKILL);
        this.baseBlock = 5;
        this.baseDamage = 10;
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.FES);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeBlock(3);
            upgradeMagicNumber(3);
        }
    }

    @Override
    public void update() {
        if (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                Shadowverse.Accelerate(this)) {
            setCostForTurn(0);
            this.type = CardType.SKILL;
        } else {
            if (this.type == CardType.SKILL) {
                setCostForTurn(2);
                this.type = CardType.ATTACK;
            }
        }
        super.update();
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("RoomServiceDevil"));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if (p.hand.group.size() < 4) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            addToBot(new GainBlockAction(p, this.block));
        }
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("RoomServiceDevil_Acc"));
        addToBot(new DiscardAction(p, p, 1, false));
        addToBot(new DamageAction(m, new DamageInfo(p, this.magicNumber, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new RoomServiceDevil();
    }
}

