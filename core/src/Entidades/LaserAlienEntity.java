package Entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.spacegame.Constantes;
import com.spacegame.ConstantesFisicas;

public class LaserAlienEntity extends Actor {
    //textura del laser
    private Texture texture;

    //instancia del mundo
    private World world;

    //cuerpo del laser
    private Body body;

    //fixture del laser
    private Fixture fixture;

    //boolean para saber si el laser está vivo
    private boolean alive, stop;

    //filtro para las colisiones
    private Filter filter;


    private String name = "laserAlien";

    public LaserAlienEntity(World world, Texture texture, Vector2 posicion) {
        this.world = world;
        this.texture = texture;

        alive = true;
        stop = false;
        //Creación del cuerpo del laser
        //defición del body
        BodyDef def = new BodyDef();
        //posición inicial
        def.position.set(posicion.x, posicion.y);
        //tipo de body
        def.type = BodyDef.BodyType.DynamicBody;
        //creamos el body
        body = world.createBody(def);
        body.setLinearVelocity(0,-5.5f);
        //Caja para las físicas
        //forma
        PolygonShape box = new PolygonShape();
        //tamaño caja en metros
        box.setAsBox(0.05f, 0.1f);
        //crea la fixture
        fixture = body.createFixture(box, 3);
        //nombre de la fixture para ser usada en maingame
        fixture.setUserData("laserAlien");
        //filtro para evitar colisiones
        filter = new Filter();
        filter.categoryBits = ConstantesFisicas.CAT_ALIEN;
        filter.maskBits= ConstantesFisicas.MASK_ALIEN;
        filter.groupIndex= -1;
        fixture.setFilterData(filter);
        //se destruye la forma que ya no hace falta
        box.dispose();

        //se pone en un tamaño para que se vea, hay que usar la clase Constantes
        setSize(Constantes.PIXEL_A_METRO/3, Constantes.PIXEL_A_METRO/3 );


    }

    /**
     * método para pintar
     * @param batch Batch
     * @param parentAlpha trasparencia
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        // hay que pintar el cuerpo de la nave según se mueva usando las constantes
        setPosition((body.getPosition().x - 0.05f) * Constantes.PIXEL_A_METRO,
                (body.getPosition().y - 0.1f) * Constantes.PIXEL_A_METRO);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    /**
     * metodo para que actualice es escenario
     * @param delta en segundos
     */
    @Override
    public void act(float delta) {
        //se cuenta la vida
        if (isAlive()) {
            vidaLaser();
        }
        //para para al laser
        if (stop){
            body.setLinearVelocity(0,0);
        }
        //se elimina si no tiene que estar
        if (!isAlive()){
            remove();
        }
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
    /**
     * método para que el laser desaparezca solo al cabe de un tiempo
     */
    public void vidaLaser(){
        if (body.getPosition().y * Constantes.PIXEL_A_METRO < 0 ){
            alive = false;
        }
    }
    @Override
    public String getName() {
        return name;
    }
    public void setName (Integer i) {
        name = name + i.toString();
        fixture.setUserData(name);
    }
    /**
     * método para que cuando un laser está muerto no pueda chocar con el jugador
     * por si no fuese eliminado del juego de manera inmediata tras morir
     */
    public void cambiaGrupo(){
        filter.groupIndex= -2;
        fixture.setFilterData(filter);
    }
    /**
     * método para para los lasers
     * @param stop boolean
     */
    public void setStop(boolean stop){
        this.stop= stop;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

}
