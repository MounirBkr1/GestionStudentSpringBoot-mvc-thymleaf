package com.mnr.gestioneleves.web;

import com.mnr.gestioneleves.entities.Etudiant;
import com.mnr.gestioneleves.repos.EtudiantRepository;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;


import javax.validation.Valid;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@Controller
//appeler lecontroller avec /Etudiant
@RequestMapping("/Etudiant")
public class EtudiantController {
    //qd il va instancier controller, va chercher l'objet qui implemente cette interface et l'injecter
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Value("${dir-images}") //@value => injecter les proprietées qui sont dans application.properties
    private String imageDir;

    //appeler methode avec /Etudiant/etudiants
    @RequestMapping(value="/index")
    /*@RequestParam(name="page") int p =>demande a dispatherServlet de regarder dans
      l'objet request.getParameter,cherche moi un parametre qui s'appelle page
      et l'affecter a 'p'.
      *defaultValue="0" => s'il trouve aucune page; il retourne page 0
      *findAll(PageRequest.of(p,5)=>retourne 5 eleves par page
     */
    public String Index(Model model ,@RequestParam(name="page",defaultValue="0") int p,
                        @RequestParam(name="motCle",defaultValue="") String mc){

        //afficher toutes les pages
        //Page<Etudiant> pageEtudiants = etudiantRepository.findAll(PageRequest.of(p,5));

        //chercher etudiant par son nom
        Page<Etudiant> pageEtudiants = etudiantRepository.chercherEtudiants("%"+mc+"%",PageRequest.of(p,5));


        //attribuer des nombre de pages
        int pageCount= pageEtudiants.getTotalPages();
        int[] pages= new int[pageCount];
        for(int i=0;i<pageCount;i++){
            pages[i]=i;
        }


        //stoquer list etudiant dans le model ===request dans servlet
        model.addAttribute("pages",pages);

        model.addAttribute("PageEtudiants", pageEtudiants);
        model.addAttribute("pageCourante",p);
        model.addAttribute("motCle",mc);
        //return la vue etudiant.html
        return "etudiants";
    }


    //quand enregistrer etudiant; il retourne donnée sur meme forme html;juste pour test
    @RequestMapping(value="/form", method= RequestMethod.GET)
    public String formEtudiant(Model model){
        model.addAttribute("etudiant",new Etudiant());
        return "FormeEtudiant";
    }




    @RequestMapping(value="/saveEtudiant", method= RequestMethod.POST)
        //stoquer les parametres ds un objet Etudiant
        //@Valid = enregistrer si tt est valid
        //s il ya des erreurs va les stoquer dans bindingResult
        //MultipartFile : recuperer image
    public String saveEtudiant(@Valid Etudiant etudiant, BindingResult bindingResult,@RequestParam(name="picture") MultipartFile file) throws IOException {
        if(bindingResult.hasErrors()){
            return "FormeEtudiant";
        }

        //creer dossier de stockage
        File f = new File(imageDir);
        try{
            if(f.mkdir()) {
                System.out.println("Directory Created");
            } else {
                System.out.println("Directory is not created");
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        if(!file.isEmpty()){
            //enregistrer photo avec son nom d'origine dans "base de donnée"
            etudiant.setPhoto(file.getOriginalFilename());

        }
        etudiantRepository.save(etudiant);

        //enregistrer etudiant
        etudiantRepository.save(etudiant);

        if(! file.isEmpty()){

             // recuperer nom de la photo
            etudiant.setPhoto(file.getOriginalFilename());

              //enregistrer file dans system de fichier
                //1 ere methode aveclien direct
            //file.transferTo(new File("C:\\Users\\pekit\\sco\\"+file.getOriginalFilename()));
                //2eme methode avec System.getProperty
            //file.transferTo(new File(System.getProperty("user.home")+"/sco/"+file.getOriginalFilename()));
                //3eme methode; creer proprité dans aplication.properties;imageDir=> dossire de stockage
            //file.transferTo(new File(imageDir + file.getOriginalFilename()));

            //4eme enregistrer etudiant avec son id comme nom de photo pour pouvoir recuperer la photo
            //l image est stoqué avec l'id etudiant dans "le dossier"
            file.transferTo(new File(imageDir + etudiant.getId()));
        }



           //si "redirect:/Etudiant/index" ou "redirect:index"=> action dans meme controleur qui est /Etudiant
        return "redirect:index";
    }


    //retourner tableau de byte et afficher photo; import org.springframework.http.MediaType;
    @RequestMapping(value="/getPhoto", produces= MediaType.IMAGE_JPEG_VALUE)
    //ce qui va etre retourné par cette methode va etre envoyer dans lecorps de la reponse
    @ResponseBody
    public byte[] getPhoto(Long id) throws Exception {
        File f=new File(imageDir + id);
        //retourner au format binaire
        //ort org.apache.commons.io.IOUtils; if dont existe add dependency "commons-io"
        return IOUtils.toByteArray(new FileInputStream(f));
    }

    @RequestMapping(value="/supprimer")
    public String supprimer(Long id){
        etudiantRepository.deleteById(id);
        return "redirect:index";
    }

    @RequestMapping(value="/edit")
    public String edit(Long id,Model model){
        Etudiant et=etudiantRepository.getById(id);
        model.addAttribute("etudiant",et);
        return "editEtudiant";
    }


    @RequestMapping(value="/UpdateEtudiant", method= RequestMethod.POST)
    //stoquer les parametres ds un objet Etudiant
    //@Valid = enregistrer si tt est valid
    //s il ya des erreurs va les stoquer dans bindingResult
    //MultipartFile : recuperer image
    public String updateEtudiant(@Valid Etudiant etudiant, BindingResult bindingResult,@RequestParam(name="picture") MultipartFile file) throws IOException {
        if(bindingResult.hasErrors()){
            //s'il ya erreur je reviens sur même formulaire
            return "EditEtudiant";
        }


        if(!file.isEmpty()){
            //enregistrer photo avec son nom d'origine dans "base de donnée"
            etudiant.setPhoto(file.getOriginalFilename());
        }

        etudiantRepository.save(etudiant);

        //enregistrer etudiant
        etudiantRepository.save(etudiant);

        if(! file.isEmpty()){

            // recuperer nom de la photo
            etudiant.setPhoto(file.getOriginalFilename());

            file.transferTo(new File(imageDir + etudiant.getId()));
        }


        return "redirect:index";
    }


}
