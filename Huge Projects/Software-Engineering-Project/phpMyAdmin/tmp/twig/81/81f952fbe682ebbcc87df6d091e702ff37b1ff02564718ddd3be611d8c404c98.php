<?php

use Twig\Environment;
use Twig\Error\LoaderError;
use Twig\Error\RuntimeError;
use Twig\Extension\SandboxExtension;
use Twig\Markup;
use Twig\Sandbox\SecurityError;
use Twig\Sandbox\SecurityNotAllowedTagError;
use Twig\Sandbox\SecurityNotAllowedFilterError;
use Twig\Sandbox\SecurityNotAllowedFunctionError;
use Twig\Source;
use Twig\Template;

/* preferences/forms/main.twig */
class __TwigTemplate_c006ee59183d582ccd07db5b88152b058a036802c39d6cca266171297b2e9f6a extends \Twig\Template
{
    private $source;
    private $macros = [];

    public function __construct(Environment $env)
    {
        parent::__construct($env);

        $this->source = $this->getSourceContext();

        $this->parent = false;

        $this->blocks = [
        ];
    }

    protected function doDisplay(array $context, array $blocks = [])
    {
        $macros = $this->macros;
        // line 1
        echo ($context["error"] ?? null);
        echo "
";
        // line 2
        if (($context["has_errors"] ?? null)) {
            // line 3
            echo "  <div class=\"error config-form\">
    <strong>";
            // line 4
            echo _gettext("Cannot save settings, submitted form contains errors!");
            echo "</strong>
    ";
            // line 5
            echo ($context["errors"] ?? null);
            echo "
  </div>
";
        }
        // line 8
        echo ($context["form"] ?? null);
        echo "
";
    }

    public function getTemplateName()
    {
        return "preferences/forms/main.twig";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  56 => 8,  50 => 5,  46 => 4,  43 => 3,  41 => 2,  37 => 1,);
    }

    public function getSourceContext()
    {
        return new Source("", "preferences/forms/main.twig", "C:\\xampp\\phpMyAdmin\\templates\\preferences\\forms\\main.twig");
    }
}
