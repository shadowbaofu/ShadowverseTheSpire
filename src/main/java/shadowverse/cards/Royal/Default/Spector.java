package shadowverse.cards.Royal.Default;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Temp.*;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.Royal;
import shadowverse.powers.NextTurnV;

import java.util.ArrayList;

public class Spector extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Spector";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Spector");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Spector.png";
    private float rotationTimer;
    private int previewIndex;

    public static ArrayList<AbstractCard> returnProphecy() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new V());
        list.add(new DesperadosShot());
        return list;
    }

    public Spector() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Royal.Enums.COLOR_YELLOW, CardRarity.RARE, CardTarget.ENEMY, 0, CardType.SKILL);
        this.baseDamage = 25;
        this.exhaust = true;
    }

    @Override
    public void update() {
        super.update();
        if (this.hb.hovered) {
            if (this.rotationTimer <= 0.0F) {
                this.rotationTimer = 2.0F;
                this.cardsToPreview = returnProphecy().get(previewIndex).makeCopy();
                if (this.previewIndex == returnProphecy().size() - 1) {
                    this.previewIndex = 0;
                } else {
                    this.previewIndex++;
                }
            } else {
                this.rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(10);
        }
    }


    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction(ID.replace("shadowverse:", "")));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ApplyPowerAction(p, p, new NextTurnV(p, 1)));
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(new DesperadosShot()));
    }


    @Override
    public AbstractCard makeCopy() {
        return new Spector();
    }
}

